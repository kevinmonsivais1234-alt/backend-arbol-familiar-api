package com.apifamilia.backend.repository;

import com.apifamilia.backend.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
  Optional<Usuario> findByEmailIgnoreCase(String email);
}
