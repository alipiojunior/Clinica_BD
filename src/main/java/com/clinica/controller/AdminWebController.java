package com.clinica.controller;

import com.clinica.model.Tutor;
import com.clinica.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin-panel") // Este é o prefixo para todas as URLs deste controller
public class AdminWebController {

    private final TutorService tutorService;

    @Autowired
    public AdminWebController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    // Exibe a página do painel do administrador com a lista de tutores
    @GetMapping
    public String showAdminPanel(Model model) {
        List<Tutor> tutores = tutorService.getAllTutors();
        model.addAttribute("tutores", tutores);
        return "admin"; // Retorna o template admin.html
    }

    // Processa a criação de um novo tutor
    @PostMapping("/tutors")
    public String createTutor(@RequestParam String nome,
                              @RequestParam String cpf,
                              @RequestParam String email,
                              RedirectAttributes redirectAttributes) {
        try {
            Tutor novoTutor = new Tutor();
            novoTutor.setNome(nome);
            novoTutor.setCpf(cpf);
            novoTutor.setEmail(email);
            tutorService.createTutor(novoTutor);
            redirectAttributes.addFlashAttribute("message", "Tutor criado com sucesso! ID: " + novoTutor.getIdTutor());
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao criar tutor: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/admin-panel"; // Redireciona de volta para a mesma página para mostrar a lista atualizada
    }

    // Processa a deleção de um tutor
    @PostMapping("/tutors/delete")
    public String deleteTutor(@RequestParam int idTutor,
                              RedirectAttributes redirectAttributes) {
        try {
            tutorService.deleteTutor(idTutor);
            redirectAttributes.addFlashAttribute("message", "Tutor com ID " + idTutor + " deletado com sucesso!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erro ao deletar tutor: " + e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/admin-panel"; // Redireciona de volta
    }
}
