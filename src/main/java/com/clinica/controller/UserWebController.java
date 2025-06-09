// src/main/java/com/clinica/controller/UserWebController.java
package com.clinica.controller;

import com.clinica.model.Animal;
import com.clinica.model.Consulta;
import com.clinica.service.AnimalService;
import com.clinica.service.ConsultaService;
import com.clinica.service.TutorService; // Pode ser necessário para validação de ID de tutor
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate; // Para input de data
import java.util.List;

@Controller
@RequestMapping("/user-panel")
public class UserWebController {

    private final AnimalService animalService;
    private final ConsultaService consultaService;
    private final TutorService tutorService; // Injetar para validar ID de tutor

    @Autowired
    public UserWebController(AnimalService animalService, ConsultaService consultaService, TutorService tutorService) {
        this.animalService = animalService;
        this.consultaService = consultaService;
        this.tutorService = tutorService;
    }

    // Exibe a página do painel do usuário com listas de animais e consultas
    @GetMapping
    public String showUserPanel(Model model) {
        model.addAttribute("animais", animalService.getAllAnimals());
        model.addAttribute("consultas", consultaService.getAllConsultas());
        return "user"; // Retorna o template user.html
    }

    // --- Métodos para Animais ---

    // Processa a criação de um novo animal
    @PostMapping("/animals")
    public String createAnimal(@RequestParam String nome,
                               @RequestParam String especie,
                               @RequestParam String raca,
                               @RequestParam int idTutor,
                               RedirectAttributes redirectAttributes) {
        try {
            // Validação simples: verificar se o tutor existe
            if (tutorService.getTutorById(idTutor) == null) {
                throw new IllegalArgumentException("Tutor com ID " + idTutor + " não encontrado.");
            }

            Animal novoAnimal = new Animal();
            novoAnimal.setNome(nome);
            novoAnimal.setEspecie(especie);
            novoAnimal.setRaca(raca);
            novoAnimal.setIdTutor(idTutor);
            animalService.createAnimal(novoAnimal);
            redirectAttributes.addFlashAttribute("message", "Animal criado com sucesso! ID: " + novoAnimal.getIdAnimal());
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao criar animal: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/user-panel";
    }

    // Processa a busca de um animal por ID
    @GetMapping("/animals/search")
    public String searchAnimal(@RequestParam int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Animal animal = animalService.getAnimalById(id);
            if (animal != null) {
                model.addAttribute("animalEncontrado", animal);
                model.addAttribute("message", "Animal encontrado.");
                model.addAttribute("messageType", "info");
            } else {
                redirectAttributes.addFlashAttribute("message", "Animal com ID " + id + " não encontrado.");
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/user-panel";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao buscar animal: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/user-panel";
        }
        model.addAttribute("animais", animalService.getAllAnimals()); // Recarrega a lista
        model.addAttribute("consultas", consultaService.getAllConsultas());
        return "user"; // Retorna o template para exibir o resultado da busca
    }

    // Processa a deleção de um animal
    @PostMapping("/animals/delete")
    public String deleteAnimal(@RequestParam int id,
                               RedirectAttributes redirectAttributes) {
        try {
            animalService.deleteAnimal(id);
            redirectAttributes.addFlashAttribute("message", "Animal com ID " + id + " deletado com sucesso!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao deletar animal: " + e.getMessage() + ". Verifique se existem consultas associadas a este animal.");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/user-panel";
    }

    // --- Métodos para Consultas ---

    // Processa a criação de uma nova consulta
    @PostMapping("/consultas")
    public String createConsulta(@RequestParam int idAnimal,
                                 @RequestParam("dataHora") @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataHora, // Converte a string para LocalDate
                                 @RequestParam String veterinario,
                                 @RequestParam String diagnostico,
                                 RedirectAttributes redirectAttributes) {
        try {
            // Validação simples: verificar se o animal existe
            if (animalService.getAnimalById(idAnimal) == null) {
                throw new IllegalArgumentException("Animal com ID " + idAnimal + " não encontrado.");
            }

            Consulta novaConsulta = new Consulta();
            novaConsulta.setIdAnimal(idAnimal);
            novaConsulta.setDataHora(dataHora);
            novaConsulta.setVeterinario(veterinario);
            novaConsulta.setDiagnostico(diagnostico);
            consultaService.createConsulta(novaConsulta);
            redirectAttributes.addFlashAttribute("message", "Consulta criada com sucesso! ID: " + novaConsulta.getIdConsulta());
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao criar consulta: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/user-panel";
    }

    // Processa a atualização de uma consulta existente
    @PostMapping("/consultas/update")
    public String updateConsulta(@RequestParam int idConsulta,
                                 @RequestParam(required = false) Integer idAnimal, // Opcional
                                 @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataHora, // Opcional
                                 @RequestParam(required = false) String veterinario, // Opcional
                                 @RequestParam(required = false) String diagnostico, // Opcional
                                 RedirectAttributes redirectAttributes) {
        try {
            Consulta consultaExistente = consultaService.getConsultaById(idConsulta);
            if (consultaExistente == null) {
                throw new IllegalArgumentException("Consulta com ID " + idConsulta + " não encontrada.");
            }

            // Atualiza apenas os campos que foram fornecidos
            if (idAnimal != null) {
                // Validação para o novo ID de animal, se fornecido
                if (animalService.getAnimalById(idAnimal) == null) {
                    throw new IllegalArgumentException("Novo Animal com ID " + idAnimal + " não encontrado.");
                }
                consultaExistente.setIdAnimal(idAnimal);
            }
            if (dataHora != null) {
                consultaExistente.setDataHora(dataHora);
            }
            if (veterinario != null && !veterinario.isEmpty()) {
                consultaExistente.setVeterinario(veterinario);
            }
            if (diagnostico != null && !diagnostico.isEmpty()) {
                consultaExistente.setDiagnostico(diagnostico);
            }

            consultaService.updateConsulta(consultaExistente);
            redirectAttributes.addFlashAttribute("message", "Consulta com ID " + idConsulta + " atualizada com sucesso!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao atualizar consulta: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/user-panel";
    }

    // Processa a busca de uma consulta por ID
    @GetMapping("/consultas/search")
    public String searchConsulta(@RequestParam int id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Consulta consulta = consultaService.getConsultaById(id);
            if (consulta != null) {
                model.addAttribute("consultaEncontrada", consulta);
                model.addAttribute("message", "Consulta encontrada.");
                model.addAttribute("messageType", "info");
            } else {
                redirectAttributes.addFlashAttribute("message", "Consulta com ID " + id + " não encontrada.");
                redirectAttributes.addFlashAttribute("messageType", "error");
                return "redirect:/user-panel";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao buscar consulta: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/user-panel";
        }
        model.addAttribute("animais", animalService.getAllAnimals()); // Recarrega a lista
        model.addAttribute("consultas", consultaService.getAllConsultas());
        return "user"; // Retorna o template para exibir o resultado da busca
    }

    // Processa a deleção de uma consulta
    @PostMapping("/consultas/delete")
    public String deleteConsulta(@RequestParam int id,
                                 RedirectAttributes redirectAttributes) {
        try {
            consultaService.deleteConsulta(id);
            redirectAttributes.addFlashAttribute("message", "Consulta com ID " + id + " deletada com sucesso!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao deletar consulta: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/user-panel";
    }
}