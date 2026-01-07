package com.fedelabsv4.controller;

import com.fedelabsv4.model.Texto;
import com.fedelabsv4.service.TextoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TextoController {
    
    @Autowired
    private TextoService textoService;
    
    @GetMapping("/")
    public String mostrarFormulario(Model model) {
        model.addAttribute("textos", textoService.obtenerTodosLosTextos());
        return "index";
    }
    
    @PostMapping("/guardar")
    public String guardarTexto(@RequestParam String nombre) {
        Texto texto = new Texto(nombre);
        textoService.guardarTexto(texto);
        return "redirect:/";
    }
}
