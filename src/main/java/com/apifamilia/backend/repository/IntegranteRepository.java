package com.apifamilia.backend.repository;

import com.apifamilia.backend.model.Integrante;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntegranteRepository extends JpaRepository<Integrante, Integer> {
  List<Integrante> findAllByOrderByIdDesc();
}
