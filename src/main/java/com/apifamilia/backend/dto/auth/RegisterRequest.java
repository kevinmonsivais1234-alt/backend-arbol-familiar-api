package com.apifamilia.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
  @NotBlank(message = "El nombre completo es obligatorio")
  @Size(max = 120, message = "El nombre no puede exceder 120 caracteres")
  String nombreCompleto,
  @Email(message = "Correo no valido") @NotBlank(message = "El correo es obligatorio") String email,
  @NotBlank(message = "La contrasena es obligatoria")
  @Size(min = 6, message = "La contrasena debe tener al menos 6 caracteres")
  String password
) {}
