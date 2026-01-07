package com.fedelabsv4.service;

import com.fedelabsv4.dominio.Project;
import com.fedelabsv4.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    public List<Project> getAllProjects() {
        return projectRepository.findAllByOrderByUpdatedAtDesc();
    }
    
    public Optional<Project> getProjectBySlug(String slug) {
        return projectRepository.findBySlug(slug);
    }
    
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }
}
