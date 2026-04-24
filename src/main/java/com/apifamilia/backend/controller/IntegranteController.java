package com.apifamilia.backend.controller;

import com.apifamilia.backend.model.Integrante;
import com.apifamilia.backend.service.IntegranteService;
import jakarta.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(originPatterns = { "http://localhost:4200", "https://*.netlify.app" })
public class IntegranteController {

  private final IntegranteService integranteService;

  public IntegranteController(IntegranteService integranteService) {
    this.integranteService = integranteService;
  }

  @GetMapping("/health")
  public Map<String, Object> health() {
    Map<String, Object> response = new LinkedHashMap<>();
    response.put("ok", true);
    response.put("message", "API Spring Boot y MySQL conectados");
    return response;
  }

  @GetMapping("/integrantes")
  public List<Integrante> obtenerIntegrantes() {
    return integranteService.obtenerIntegrantes();
  }

  @PostMapping("/integrantes")
  public ResponseEntity<Integrante> guardarIntegrante(@Valid @RequestBody Integrante integrante) {
    Integrante guardado = integranteService.guardarIntegrante(integrante);
    return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new LinkedHashMap<>();

    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }

    return ResponseEntity.badRequest().body(errors);
  }
}
