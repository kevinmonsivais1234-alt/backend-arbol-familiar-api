package com.apifamilia.backend.service;

import com.apifamilia.backend.dto.auth.AuthResponse;
import com.apifamilia.backend.dto.auth.GoogleLoginRequest;
import com.apifamilia.backend.dto.auth.LoginRequest;
import com.apifamilia.backend.dto.auth.RegisterRequest;
import com.apifamilia.backend.model.ProveedorAuth;
import com.apifamilia.backend.model.Usuario;
import com.apifamilia.backend.repository.UsuarioRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

@Service
public class AuthService {

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private final RestTemplate restTemplate = new RestTemplate();

  @Value("${GOOGLE_CLIENT_ID:}")
  private String googleClientId;

  public AuthService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  public AuthResponse registrar(RegisterRequest request) {
    String emailNormalizado = normalizarEmail(request.email());

    if (usuarioRepository.findByEmailIgnoreCase(emailNormalizado).isPresent()) {
      throw new IllegalStateException("Ese correo ya esta registrado.");
    }

    Usuario usuario = new Usuario();
    usuario.setNombreCompleto(request.nombreCompleto().trim());
    usuario.setEmail(emailNormalizado);
    usuario.setPasswordHash(passwordEncoder.encode(request.password()));
    usuario.setProveedor(ProveedorAuth.LOCAL);

    Usuario guardado = usuarioRepository.save(usuario);
    return toResponse(guardado);
  }

  public AuthResponse login(LoginRequest request) {
    String emailNormalizado = normalizarEmail(request.email());

    Usuario usuario = usuarioRepository
      .findByEmailIgnoreCase(emailNormalizado)
      .orElseThrow(() -> new IllegalArgumentException("Correo o contrasena incorrectos."));

    if (usuario.getPasswordHash() == null || usuario.getPasswordHash().isBlank()) {
      throw new IllegalArgumentException("Esta cuenta fue creada con Google. Usa Login Google.");
    }

    if (!passwordEncoder.matches(request.password(), usuario.getPasswordHash())) {
      throw new IllegalArgumentException("Correo o contrasena incorrectos.");
    }

    return toResponse(usuario);
  }

  public AuthResponse loginConGoogle(GoogleLoginRequest request) {
    Map<String, Object> tokenInfo = validarTokenGoogle(request.credential());
    String email = normalizarEmail(leerCadena(tokenInfo, "email"));
    String nombre = leerCadena(tokenInfo, "name");
    String foto = leerCadena(tokenInfo, "picture");

    if (email.isBlank()) {
      throw new IllegalArgumentException("No se pudo obtener el correo de Google.");
    }

    Usuario usuario = usuarioRepository
      .findByEmailIgnoreCase(email)
      .orElseGet(() -> {
        Usuario nuevo = new Usuario();
        nuevo.setEmail(email);
        nuevo.setProveedor(ProveedorAuth.GOOGLE);
        return nuevo;
      });

    usuario.setNombreCompleto(nombre.isBlank() ? "Usuario Google" : nombre);
    usuario.setFotoUrl(foto.isBlank() ? null : foto);
    if (usuario.getProveedor() == null) {
      usuario.setProveedor(ProveedorAuth.GOOGLE);
    }

    Usuario guardado = usuarioRepository.save(usuario);
    return toResponse(guardado);
  }

  private Map<String, Object> validarTokenGoogle(String idToken) {
    String tokenCodificado = UriUtils.encodePathSegment(idToken, java.nio.charset.StandardCharsets.UTF_8);
    String url = "https://oauth2.googleapis.com/tokeninfo?id_token=" + tokenCodificado;

    try {
      ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
      Map<String, Object> body = response.getBody();

      if (body == null || body.isEmpty()) {
        throw new IllegalArgumentException("No se pudo validar el token de Google.");
      }

      String aud = leerCadena(body, "aud");
      if (!googleClientId.isBlank() && !googleClientId.equals(aud)) {
        throw new IllegalArgumentException("El token de Google no corresponde a este proyecto.");
      }

      String emailVerified = leerCadena(body, "email_verified");
      if (!"true".equalsIgnoreCase(emailVerified) && !"1".equals(emailVerified)) {
        throw new IllegalArgumentException("Google no confirmo el correo de esta cuenta.");
      }

      return body;
    } catch (RestClientException ex) {
      throw new IllegalArgumentException("No se pudo validar el token con Google.");
    }
  }

  private String normalizarEmail(String email) {
    if (email == null) {
      return "";
    }
    return email.trim().toLowerCase();
  }

  private String leerCadena(Map<String, Object> origen, String llave) {
    Object valor = origen.get(llave);
    return valor == null ? "" : String.valueOf(valor).trim();
  }

  private AuthResponse toResponse(Usuario usuario) {
    return new AuthResponse(
      usuario.getId(),
      usuario.getNombreCompleto(),
      usuario.getEmail(),
      usuario.getProveedor().name(),
      usuario.getFotoUrl()
    );
  }
}
