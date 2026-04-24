package com.apifamilia.backend.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record GoogleLoginRequest(
  @NotBlank(message = "Token de Google no valido") String credential
) {}
