package com.fedelabsv4.dto;

import com.fedelabsv4.model.Comentario;

public class ComentarioDTO {

    private final String id;
    private final String contenido;
    private final String autor;
    private final String fechaCreacion;

    private ComentarioDTO(
            String id,
            String contenido,
            String autor,
            String fechaCreacion) {

        this.id = id;
        this.contenido = contenido;
        this.autor = autor;
        this.fechaCreacion = fechaCreacion;
    }

    public static ComentarioDTO fromEntity(Comentario comentario) {
        return new ComentarioDTO(
                comentario.getId(),
                comentario.getContenido(),
                comentario.getAutor(),
                comentario.getFechaCreacion().toString()
        );
    }

    public String getId() {
        return id;
    }

    public String getContenido() {
        return contenido;
    }

    public String getAutor() {
        return autor;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }
}