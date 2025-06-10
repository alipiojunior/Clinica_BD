// src/main/java/com/clinica/ClinicaApplication.java
package com.clinica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan; // <--- ADICIONE ESTE IMPORT

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ClinicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicaApplication.class, args);
        System.out.println("Aplicação de Clínica iniciada! Acesse: http://localhost:8080/");
    }
}