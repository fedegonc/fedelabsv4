package com.fedelabsv4.service;

import com.fedelabsv4.dominio.Idea;
import com.fedelabsv4.repository.IdeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdeaService {
    
    @Autowired
    private IdeaRepository ideaRepository;
    
    public List<Idea> getAllIdeas() {
        return ideaRepository.findAllByOrderByUpdatedAtDesc();
    }
    
    public List<Idea> getActiveIdeas() {
        return ideaRepository.findByStatusOrderByUpdatedAtDesc("activa");
    }
    
    public List<Idea> getDiscardedIdeas() {
        return ideaRepository.findByStatusOrderByUpdatedAtDesc("descartada");
    }
    
    public Idea saveIdea(Idea idea) {
        return ideaRepository.save(idea);
    }
}
