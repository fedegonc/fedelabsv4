package com.fedelabsv4.controller;

import com.fedelabsv4.dto.ApunteDTO;
import com.fedelabsv4.model.Apunte;
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

        ApunteDTO apunteDTO =
                apunteService.obtenerApunteConComentarios(slug);

        if (!apunteDTO.isDisponible()) {
            return "redirect:/apuntes";
        }

        model.addAttribute("apunte", apunteDTO);
        model.addAttribute("comentarios",
                apunteDTO.getComentarios());

        return "apuntes/detalle";
    }

    @GetMapping("/admin/apuntes")
    public String adminApuntes(Model model) {
        model.addAttribute("apuntes",
                apunteService.obtenerTodos());

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
    public String editarApunte(
            @PathVariable String id,
            Model model) {

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
            @PathVariable String id,
            @ModelAttribute Apunte apunte) {

        return apunteService.actualizar(id, apunte)
                .map(apunteActualizado ->
                        "redirect:/apuntes/" +
                                apunteActualizado.getSlug())
                .orElse("redirect:/admin/apuntes");
    }

    @PostMapping("/admin/apuntes/eliminar/{id}")
    public String eliminarApunte(
            @PathVariable String id) {

        apunteService.eliminar(id);

        return "redirect:/admin/apuntes";
    }
}