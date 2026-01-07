package com.fedelabsv4.controller;

import com.fedelabsv4.dominio.Idea;
import com.fedelabsv4.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IdeaController {
    
    @Autowired
    private IdeaService ideaService;
    
    @GetMapping("/ideas")
    public String listIdeas(Model model) {
        List<Idea> ideas = ideaService.getAllIdeas();
        model.addAttribute("ideas", ideas);
        model.addAttribute("content", "ideas/list");
        return "layout/base";
    }
}
