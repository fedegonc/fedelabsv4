package com.fedelabsv4.dto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.fedelabsv4.model.Apunte;
import com.fedelabsv4.model.Comentario;

public class ApunteDTO {

    private final String id;

    private final String titulo;

    private final String contenido;

    private final String slug;

    private final String categoria;

    private final String imageUrl;

    private final LocalDateTime fechaCreacion;

    private final List<ComentarioDTO> comentarios;

    private final boolean disponible;

    private ApunteDTO(
            String id,
            String titulo,
            String contenido,
            String slug,
            String categoria,
            String imageUrl,
            LocalDateTime fechaCreacion,
            List<ComentarioDTO> comentarios,
            boolean disponible) {

        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.slug = slug;
        this.categoria = categoria;
        this.imageUrl = imageUrl;
        this.fechaCreacion = fechaCreacion;
        this.comentarios = comentarios != null
                ? comentarios
                : Collections.emptyList();
        this.disponible = disponible;
    }

    public static ApunteDTO disponible(
            Apunte apunte,
            List<Comentario> comentarios) {

        List<ComentarioDTO> comentariosDTO = comentarios.stream()
                .map(ComentarioDTO::fromEntity)
                .toList();

        return new ApunteDTO(
                apunte.getId(),
                apunte.getTitulo(),
                apunte.getContenido(),
                apunte.getSlug(),
                apunte.getCategoria(),
                apunte.getImageUrl(),
                apunte.getFechaCreacion(),
                comentariosDTO,
                true
        );
    }

    public static ApunteDTO noDisponible() {
        return new ApunteDTO(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                List.of(),
                false
        );
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public String getSlug() {
        return slug;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public boolean isDisponible() {
        return disponible;
    }
}