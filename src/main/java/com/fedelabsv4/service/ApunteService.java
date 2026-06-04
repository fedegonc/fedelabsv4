package com.fedelabsv4.service;

import com.fedelabsv4.dto.ApunteDTO;
import com.fedelabsv4.model.Apunte;
import com.fedelabsv4.model.Comentario;
import com.fedelabsv4.repository.ApunteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ApunteService {

    private final ApunteRepository apunteRepository;
    private final ComentarioService comentarioService;

    public ApunteService(
            ApunteRepository apunteRepository,
            ComentarioService comentarioService) {

        this.apunteRepository = apunteRepository;
        this.comentarioService = comentarioService;
    }

    @Transactional(readOnly = true)
    public List<Apunte> obtenerTodos() {
        return apunteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Apunte> obtenerPorId(String id) {
        return apunteRepository.findById(id);
    }

    public Apunte crear(Apunte apunte) {
        return apunteRepository.save(apunte);
    }

    public Optional<Apunte> actualizar(String id, Apunte apunteActualizado) {
        return apunteRepository.findById(id)
                .map(apunte -> {
                    apunte.setTitulo(apunteActualizado.getTitulo());
                    apunte.setContenido(apunteActualizado.getContenido());
                    apunte.setCategoria(apunteActualizado.getCategoria());
                    apunte.setImageUrl(apunteActualizado.getImageUrl());
                    return apunteRepository.save(apunte);
                });
    }

    public boolean eliminar(String id) {
        if (apunteRepository.existsById(id)) {
            apunteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Optional<Apunte> obtenerPorSlug(String slug) {
        return apunteRepository.findBySlug(slug);
    }

    @Transactional(readOnly = true)
    public ApunteDTO obtenerApunteConComentarios(String slug) {

        log.debug("Buscando apunte con slug: {}", slug);

        Optional<Apunte> apunteOpt = apunteRepository.findBySlug(slug);

        if (apunteOpt.isEmpty()) {
            log.warn("Apunte no encontrado con slug: {}", slug);
            return ApunteDTO.noDisponible();
        }

        Apunte apunte = apunteOpt.get();

        log.debug(
                "Apunte encontrado - ID: {}, Slug: {}",
                apunte.getId(),
                apunte.getSlug()
        );

        List<Comentario> comentarios =
                comentarioService.obtenerPorApunte(apunte.getId());

        log.debug(
                "Comentarios encontrados: {}",
                comentarios.size()
        );

        return ApunteDTO.disponible(apunte, comentarios);
    }
}