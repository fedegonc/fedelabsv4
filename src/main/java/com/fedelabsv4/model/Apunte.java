package com.fedelabsv4.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "apuntes")
public class Apunte {

    @Id
    private String id;

    private String slug;

    private String titulo;

    private String contenido;

    private String categoria;

    private String imageUrl;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

    public Apunte() {
        this.slug = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10);

        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public Apunte(
            String id,
            String titulo,
            String contenido,
            String categoria,
            String imageUrl) {

        this();

        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.categoria = categoria;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}