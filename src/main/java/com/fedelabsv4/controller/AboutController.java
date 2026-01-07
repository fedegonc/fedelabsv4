package com.fedelabsv4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class AboutController {
    
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("content", "about");
        return "layout/base";
    }
}
