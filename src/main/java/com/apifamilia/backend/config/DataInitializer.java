package com.apifamilia.backend.config;

import com.apifamilia.backend.model.Integrante;
import com.apifamilia.backend.service.IntegranteService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer implements CommandLineRunner {

  private final IntegranteService integranteService;

  public DataInitializer(IntegranteService integranteService) {
    this.integranteService = integranteService;
  }

  @Override
  public void run(String... args) {
    if (integranteService.contarIntegrantes() > 0) {
      return;
    }

    List<Integrante> base = List.of(
      crearIntegrante(
        "Kevin Alejandro",
        "Monsivais Villarreal",
        LocalDate.of(2005, 1, 26),
        "8113678493",
        "Nuevo Leon",
        "Hijo",
        "Masculino",
        "Mexicana"
      ),
      crearIntegrante(
        "Maria de los Angeles",
        "Villarreal Sanchez",
        LocalDate.of(1980, 5, 10),
        "8234562345",
        "Nuevo Leon",
        "Madre",
        "Femenino",
        "Mexicana"
      ),
      crearIntegrante(
        "Alfredo",
        "Monsivais Vara",
        LocalDate.of(1978, 3, 15),
        "8123232789",
        "Nuevo Leon",
        "Padre",
        "Masculino",
        "Mexicana"
      )
    );

    base.forEach(integranteService::guardarIntegrante);
  }

  private Integrante crearIntegrante(
    String nombre,
    String apellido,
    LocalDate fechaNacimiento,
    String telefono,
    String direccion,
    String parentesco,
    String sexo,
    String nacionalidad
  ) {
    Integrante integrante = new Integrante();
    integrante.setNombre(nombre);
    integrante.setApellido(apellido);
    integrante.setFecha_nacimiento(fechaNacimiento);
    integrante.setTelefono(telefono);
    integrante.setDireccion(direccion);
    integrante.setParentesco(parentesco);
    integrante.setSexo(sexo);
    integrante.setNacionalidad(nacionalidad);
    return integrante;
  }
}
