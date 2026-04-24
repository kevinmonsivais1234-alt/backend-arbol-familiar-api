CREATE DATABASE IF NOT EXISTS FamiliaMonsivaisVillarreal
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE FamiliaMonsivaisVillarreal;

CREATE TABLE IF NOT EXISTS integrantes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  apellido VARCHAR(50) NOT NULL,
  fecha_nacimiento DATE,
  telefono VARCHAR(15),
  direccion VARCHAR(100),
  parentesco VARCHAR(30),
  sexo VARCHAR(10),
  nacionalidad VARCHAR(30) DEFAULT 'Mexicana'
);
