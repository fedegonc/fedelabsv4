package com.fedelabsv4.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fedelabsv4.model.Comentario;
import com.fedelabsv4.repository.ComentarioRepository;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    public void guardar(Long apunteId, String autor, String contenido) {

        Comentario comentario = new Comentario();

        comentario.setApunteId(apunteId);
        comentario.setAutor(autor);
        comentario.setContenido(contenido);

        comentarioRepository.save(comentario);

        System.out.println("Comentario guardado!");

    }

    public List<Comentario> obtenerPorApunte(Long apunteId) {
    return comentarioRepository.findByApunteId(apunteId);
}
}