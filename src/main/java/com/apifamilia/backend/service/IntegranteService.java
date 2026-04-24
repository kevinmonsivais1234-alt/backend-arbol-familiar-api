package com.apifamilia.backend.service;

import com.apifamilia.backend.model.Integrante;
import com.apifamilia.backend.repository.IntegranteRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class IntegranteService {

  private final IntegranteRepository integranteRepository;

  public IntegranteService(IntegranteRepository integranteRepository) {
    this.integranteRepository = integranteRepository;
  }

  public List<Integrante> obtenerIntegrantes() {
    return integranteRepository.findAllByOrderByIdDesc();
  }

  public Integrante guardarIntegrante(Integrante integrante) {
    return integranteRepository.save(integrante);
  }

  public long contarIntegrantes() {
    return integranteRepository.count();
  }
}
