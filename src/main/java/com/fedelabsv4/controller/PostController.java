package com.fedelabsv4.controller;

import com.fedelabsv4.dominio.Post;
import com.fedelabsv4.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PostController {
    
    @Autowired
    private PostService postService;
    
    @GetMapping("/posts")
    public String listPosts(Model model) {
        List<Post> posts = postService.getAllPublishedPosts();
        model.addAttribute("posts", posts);
        model.addAttribute("content", "posts/list");
        return "layout/base";
    }
    
    @GetMapping("/posts/{slug}")
    public String viewPost(@PathVariable String slug, Model model) {
        return postService.getPostBySlug(slug)
                .map(post -> {
                    model.addAttribute("post", post);
                    model.addAttribute("content", "posts/detail");
                    return "layout/base";
                })
                .orElse("redirect:/posts");
    }
}
