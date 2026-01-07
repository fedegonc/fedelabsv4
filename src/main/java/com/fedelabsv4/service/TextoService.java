package com.fedelabsv4.service;

import com.fedelabsv4.model.Texto;
import com.fedelabsv4.repository.TextoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextoService {
    
    @Autowired
    private TextoRepository textoRepository;
    
    public Texto guardarTexto(Texto texto) {
        return textoRepository.save(texto);
    }
    
    public List<Texto> obtenerTodosLosTextos() {
        return textoRepository.findAll();
    }
}
