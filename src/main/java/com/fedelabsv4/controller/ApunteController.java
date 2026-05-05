package com.fedelabsv4.controller;

import com.fedelabsv4.model.Apunte;
import com.fedelabsv4.service.ApunteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ApunteController {
    
    @Autowired
    private ApunteService apunteService;
    
    @GetMapping("/apuntes")
    public String listarApuntes(Model model) {
        model.addAttribute("apuntes", apunteService.obtenerTodos());
        return "apuntes/lista";
    }
    
    @GetMapping("/apuntes/{id}")
    public String detalleApunte(@PathVariable Long id, Model model) {
        return apunteService.obtenerPorId(id)
                .map(apunte -> {
                    model.addAttribute("apunte", apunte);
                    return "apuntes/detalle";
                })
                .orElse("redirect:/apuntes");
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
        apunteService.crear(apunte);
        return "redirect:/admin/apuntes";
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
    public String actualizarApunte(@PathVariable Long id, @ModelAttribute Apunte apunte) {
        apunteService.actualizar(id, apunte);
        return "redirect:/admin/apuntes";
    }
    
    @PostMapping("/admin/apuntes/eliminar/{id}")
    public String eliminarApunte(@PathVariable Long id) {
        apunteService.eliminar(id);
        return "redirect:/admin/apuntes";
    }
}
