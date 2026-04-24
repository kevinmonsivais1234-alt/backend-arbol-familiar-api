package com.apifamilia.backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiFamiliaMonsivaisApplication {

  public static void main(String[] args) {
    cargarVariablesDesdeEnv();
    SpringApplication.run(ApiFamiliaMonsivaisApplication.class, args);
  }

  private static void cargarVariablesDesdeEnv() {
    Dotenv dotenv = Dotenv.configure()
      .ignoreIfMalformed()
      .ignoreIfMissing()
      .load();

    dotenv.entries().forEach(entry -> {
      String key = entry.getKey();
      if (System.getenv(key) == null && System.getProperty(key) == null) {
        System.setProperty(key, entry.getValue());
      }
    });
  }
}
