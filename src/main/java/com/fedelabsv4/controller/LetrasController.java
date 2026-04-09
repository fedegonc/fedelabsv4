package com.fedelabsv4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/letras")
public class LetrasController {
    
    @GetMapping
    public String letrasPage() {
        return "letras/selector";
    }
}
