package com.fedelabsv4.controller;

import com.fedelabsv4.dominio.Post;
import com.fedelabsv4.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @Autowired
    private PostService postService;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("posts", postService.getLatestPublishedPosts(3));
        model.addAttribute("content", "home/home");
        return "layout/base";
    }
}
