package com.apifamilia.backend.dto.auth;

public record AuthResponse(
  Integer id,
  String nombreCompleto,
  String email,
  String proveedor,
  String fotoUrl
) {}
