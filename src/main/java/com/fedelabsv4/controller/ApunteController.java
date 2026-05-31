package com.fedelabsv4.controller;

import java.util.List;
import java.util.Optional;

import com.fedelabsv4.model.Apunte;
import com.fedelabsv4.model.Comentario;
import com.fedelabsv4.service.ApunteService;
import com.fedelabsv4.service.ComentarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApunteController {

    @Autowired
    private ApunteService apunteService;

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/apuntes")
    public String listarApuntes(Model model) {
        model.addAttribute("apuntes", apunteService.obtenerTodos());
        return "apuntes/lista";
    }
@GetMapping("/apuntes/{slug}")
public String verApunte(@PathVariable String slug, Model model) {

    System.out.println("Slug recibido: " + slug);

    return apunteService.obtenerPorSlug(slug)
            .map(apunte -> {

                System.out.println("APUNTE ENCONTRADO");
                System.out.println("ID: " + apunte.getId());
                System.out.println("Slug BD: " + apunte.getSlug());

                model.addAttribute("apunte", apunte);

                List<Comentario> comentarios =
                        comentarioService.obtenerPorApunte(apunte.getId());

                model.addAttribute("comentarios", comentarios);

                return "apuntes/detalle";
            })
            .orElseGet(() -> {

                System.out.println("NO SE ENCONTRO EL APUNTE");
                System.out.println("Slug buscado: " + slug);

                return "redirect:/apuntes";
            });
}




    @GetMapping("/admin/apuntes")
    public String adminApuntes(Model model) {
        model.addAttribute("apuntes", apunteService.obtenerTodos());
        return "apuntes/admin-lista";
    }

    @GetMapping("/admin/apuntes/nuevo")
    public String nuevoApunte(Model model) {
        model.addAttribute("apunte", new Apunte());
        model.addAttribute("modoEdicion", false);
        return "apuntes/admin-form";
    }

    @PostMapping("/admin/apuntes")
    public String crearApunte(@ModelAttribute Apunte apunte) {
        Apunte guardado = apunteService.crear(apunte);
        return "redirect:/apuntes/" + guardado.getSlug();
    }

    @GetMapping("/admin/apuntes/editar/{id}")
    public String editarApunte(@PathVariable Long id, Model model) {
        return apunteService.obtenerPorId(id)
                .map(apunte -> {
                    model.addAttribute("apunte", apunte);
                    model.addAttribute("modoEdicion", true);
                    return "apuntes/admin-form";
                })
                .orElse("redirect:/admin/apuntes");
    }

    @PostMapping("/admin/apuntes/editar/{id}")
    public String actualizarApunte(
            @PathVariable Long id,
            @ModelAttribute Apunte apunte) {

        return apunteService.actualizar(id, apunte)
                .map(apunteActualizado ->
                        "redirect:/apuntes/" + apunteActualizado.getSlug())
                .orElse("redirect:/admin/apuntes");
    }

    @PostMapping("/admin/apuntes/eliminar/{id}")
    public String eliminarApunte(@PathVariable Long id) {
        apunteService.eliminar(id);
        return "redirect:/admin/apuntes";
    }
}