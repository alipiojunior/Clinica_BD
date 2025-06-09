package com.clinica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Este é um Controller para servir páginas HTML
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index"; // Retorna o nome do template HTML (index.html)
    }
}
