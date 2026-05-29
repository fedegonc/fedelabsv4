package com.fedelabsv4.controller;

import com.fedelabsv4.service.ApunteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @Autowired
    private ApunteService apunteService;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("apuntes", apunteService.obtenerTodos());
        return "index";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/payment-example")
    public String paymentExample() {
        return "payment-example";
    }
    
    @GetMapping("/payment-success")
    public String paymentSuccess() {
        return "payment-success";
    }
    
    @GetMapping("/payment-cancel")
    public String paymentCancel() {
        return "payment-cancel";
    }
}
