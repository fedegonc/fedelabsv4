package com.fedelabsv4.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Apunte {

    private Long id;

    // CAMPO SLUG
    private String slug;

    private String titulo;
    private String contenido;
    private String categoria;
    private String imageUrl;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Apunte() {

        // GENERA SLUG AUTOMÁTICO
        this.slug = generarSlug();

        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    public Apunte(Long id,
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

    // MÉTODO PARA GENERAR SLUG
    private String generarSlug() {

        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // GETTER Y SETTER DEL SLUG

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