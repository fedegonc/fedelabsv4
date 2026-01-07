package com.fedelabsv4.controller;

import com.fedelabsv4.dominio.Project;
import com.fedelabsv4.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @GetMapping("/projects")
    public String listProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        model.addAttribute("content", "projects/list");
        return "layout/base";
    }
    
    @GetMapping("/projects/{slug}")
    public String viewProject(@PathVariable String slug, Model model) {
        return projectService.getProjectBySlug(slug)
                .map(project -> {
                    model.addAttribute("project", project);
                    model.addAttribute("content", "projects/detail");
                    return "layout/base";
                })
                .orElse("redirect:/projects");
    }
}
