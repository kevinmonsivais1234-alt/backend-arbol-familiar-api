package com.apifamilia.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "integrantes")
public class Integrante {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotBlank(message = "El nombre es obligatorio")
  @Column(nullable = false, length = 50)
  private String nombre;

  @NotBlank(message = "El apellido es obligatorio")
  @Column(nullable = false, length = 50)
  private String apellido;

  @NotNull(message = "La fecha de nacimiento es obligatoria")
  @Column(name = "fecha_nacimiento")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate fecha_nacimiento;

  @NotBlank(message = "El telefono es obligatorio")
  @Column(length = 15)
  private String telefono;

  @NotBlank(message = "La direccion es obligatoria")
  @Column(length = 100)
  private String direccion;

  @NotBlank(message = "El parentesco es obligatorio")
  @Column(length = 30)
  private String parentesco;

  @NotBlank(message = "El sexo es obligatorio")
  @Column(length = 10)
  private String sexo;

  @NotBlank(message = "La nacionalidad es obligatoria")
  @Column(length = 30)
  private String nacionalidad;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public LocalDate getFecha_nacimiento() {
    return fecha_nacimiento;
  }

  public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
    this.fecha_nacimiento = fecha_nacimiento;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getParentesco() {
    return parentesco;
  }

  public void setParentesco(String parentesco) {
    this.parentesco = parentesco;
  }

  public String getSexo() {
    return sexo;
  }

  public void setSexo(String sexo) {
    this.sexo = sexo;
  }

  public String getNacionalidad() {
    return nacionalidad;
  }

  public void setNacionalidad(String nacionalidad) {
    this.nacionalidad = nacionalidad;
  }
}
