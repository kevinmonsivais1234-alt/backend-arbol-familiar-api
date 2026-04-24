package com.apifamilia.backend.controller;

import com.apifamilia.backend.dto.auth.AuthResponse;
import com.apifamilia.backend.dto.auth.GoogleLoginRequest;
import com.apifamilia.backend.dto.auth.LoginRequest;
import com.apifamilia.backend.dto.auth.RegisterRequest;
import com.apifamilia.backend.service.AuthService;
import jakarta.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
    AuthResponse response = authService.registrar(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
    AuthResponse response = authService.login(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/google")
  public ResponseEntity<AuthResponse> loginConGoogle(@Valid @RequestBody GoogleLoginRequest request) {
    AuthResponse response = authService.loginConGoogle(request);
    return ResponseEntity.ok(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new LinkedHashMap<>();

    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
  public ResponseEntity<Map<String, String>> manejarNegocio(RuntimeException ex) {
    Map<String, String> response = new LinkedHashMap<>();
    response.put("error", ex.getMessage());
    return ResponseEntity.badRequest().body(response);
  }
}
