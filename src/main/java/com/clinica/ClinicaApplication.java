package com.clinica;

import com.clinica.model.Animal;
import com.clinica.model.Consulta;
import com.clinica.model.Tutor;
import com.clinica.service.AnimalService;
import com.clinica.service.ConsultaService;
import com.clinica.service.TutorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate; // Revertido para LocalDate
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ClinicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicaApplication.class, args);
        System.out.println("Aplicação de Clínica iniciada! O servidor REST está rodando em http://localhost:8080.");
        System.out.println("Para testar funcionalidades, rode o método `run` do CommandLineRunner.");
    }

    @Bean
    public CommandLineRunner run(TutorService tutorService, AnimalService animalService, ConsultaService consultaService) {
        return args -> {
            System.out.println("\n--- INICIANDO TESTES MANUAIS ---");

            // --- TESTES DE ADMINISTRADOR (Tutor) ---
            System.out.println("\n--- Testes de Administrador (Tutor) ---");

            // CREATE Tutor
            Tutor novoTutor = new Tutor();
            novoTutor.setNome("Ana Paula");
            novoTutor.setCpf("11122233344");
            novoTutor.setEmail("ana.paula@example.com");
            tutorService.createTutor(novoTutor);
            System.out.println("Tutor criado: " + novoTutor);

            // READ Todos os Tutores
            List<Tutor> todosTutores = tutorService.getAllTutors();
            System.out.println("Todos os Tutores:");
            todosTutores.forEach(System.out::println);

            // DELETE Tutor (Comentado para persistir com a "Lore" se você quiser)
            /*
            if (novoTutor.getIdTutor() != 0) {
                System.out.println("Deletando o tutor com ID: " + novoTutor.getIdTutor());
                tutorService.deleteTutor(novoTutor.getIdTutor());
                System.out.println("Verificando tutores após exclusão:");
                tutorService.getAllTutors().forEach(System.out::println);
            }
            */

            // --- TESTES DE USUÁRIO (Animal e Consulta) ---
            System.out.println("\n--- Testes de Usuário (Animal e Consulta) ---");

            // CREATE Animal
            Animal novoAnimal = new Animal();
            novoAnimal.setIdTutor(1); // Use um ID de tutor existente (ex: José tem ID 1)
            novoAnimal.setNome("Bolinha");
            novoAnimal.setEspecie("Cachorro");
            novoAnimal.setRaca("Poodle");
            animalService.createAnimal(novoAnimal);
            System.out.println("Animal criado: " + novoAnimal);

            // READ Animal por ID
            Animal animalEncontrado = animalService.getAnimalById(novoAnimal.getIdAnimal());
            System.out.println("Animal encontrado pelo ID " + novoAnimal.getIdAnimal() + ": " + animalEncontrado);

            // READ Todos os Animais
            List<Animal> todosAnimais = animalService.getAllAnimals();
            System.out.println("Todos os Animais:");
            todosAnimais.forEach(System.out::println);

            // CREATE Consulta
            Consulta novaConsulta = new Consulta();
            novaConsulta.setIdAnimal(novoAnimal.getIdAnimal()); // Usa o ID do animal recém-criado
            novaConsulta.setDataHora(LocalDate.of(2025, 6, 10)); // Use LocalDate
            novaConsulta.setVeterinario("Dr. João");
            novaConsulta.setDiagnostico("Check-up anual");
            consultaService.createConsulta(novaConsulta);
            System.out.println("Consulta criada: " + novaConsulta);

            // READ Consulta por ID
            Consulta consultaEncontrada = consultaService.getConsultaById(novaConsulta.getIdConsulta());
            System.out.println("Consulta encontrada pelo ID " + novaConsulta.getIdConsulta() + ": " + consultaEncontrada);

            // UPDATE Consulta
            if (consultaEncontrada != null) {
                consultaEncontrada.setDiagnostico("Check-up anual - tudo OK!");
                consultaEncontrada.setVeterinario("Dra. Maria");
                consultaEncontrada.setDataHora(LocalDate.of(2025, 6, 12)); // Alterando a data
                consultaService.updateConsulta(consultaEncontrada);
                System.out.println("Consulta atualizada: " + consultaService.getConsultaById(consultaEncontrada.getIdConsulta()));
            }

            // READ Todas as Consultas
            List<Consulta> todasConsultas = consultaService.getAllConsultas();
            System.out.println("Todas as Consultas:");
            todasConsultas.forEach(System.out::println);

            // DELETE Consulta (Comentado para persistir dados)
            /*
            if (novaConsulta.getIdConsulta() != 0) {
                System.out.println("Deletando a consulta com ID: " + novaConsulta.getIdConsulta());
                consultaService.deleteConsulta(novaConsulta.getIdConsulta());
                System.out.println("Verificando consultas após exclusão:");
                consultaService.getAllConsultas().forEach(System.out::println);
            }

            // DELETE Animal (após deletar a consulta que o referencia, se não estivesse comentado)
            if (novoAnimal.getIdAnimal() != 0) {
                System.out.println("Deletando o animal com ID: " + novoAnimal.getIdAnimal());
                animalService.deleteAnimal(novoAnimal.getIdAnimal());
                System.out.println("Verificando animais após exclusão:");
                animalService.getAllAnimals().forEach(System.out::println);
            }
            */

            System.out.println("\n--- FIM DOS TESTES MANUAIS ---");
        };
    }
}