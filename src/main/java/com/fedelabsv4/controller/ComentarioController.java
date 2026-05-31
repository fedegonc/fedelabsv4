package com.fedelabsv4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fedelabsv4.service.ApunteService;
import com.fedelabsv4.service.ComentarioService;

@Controller
public class ComentarioController {

    private final ComentarioService comentarioService;

    private final ApunteService apunteService;

    public ComentarioController(
        ComentarioService comentarioService,
        ApunteService apunteService) {

    this.comentarioService = comentarioService;
    this.apunteService = apunteService;
    }

    
   @PostMapping("/comentarios/guardar")
    public String guardarComentario(
        @RequestParam Long apunteId,
        @RequestParam String nombre,
        @RequestParam String contenido) {

    comentarioService.guardar(
            apunteId,
            nombre,
            contenido
    );

    return apunteService.obtenerPorId(apunteId)
            .map(apunte -> "redirect:/apuntes/" + apunte.getSlug())
            .orElse("redirect:/apuntes");
}

}