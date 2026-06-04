package com.fedelabsv4.model;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comentarios")
public class Comentario {

    private static final int MAXIMO_CONTENIDO = 1000;
    private static final String AUTOR_ANONIMO = "Anónimo";
    private static final Pattern PALABRAS_PROHIBIDAS =
            Pattern.compile("(?i)(spam|publicidad|casino|viagra)");

    @Id
    private String id;

    private String autor;
    private String contenido;
    private LocalDateTime fechaCreacion;
    private String apunteId;
    private int likes = 0;

    public Comentario() {
        this.fechaCreacion = LocalDateTime.now();
        this.autor = AUTOR_ANONIMO;
    }

    public Comentario(String autor, String contenido, String apunteId) {
        this();
        this.setAutor(autor);
        this.setContenido(contenido);
        this.apunteId = apunteId;
    }

    public boolean esContenidoValido() {
        return contenido != null
                && contenido.length() >= 2
                && contenido.length() <= MAXIMO_CONTENIDO
                && !contienePalabrasProhibidas();
    }

    public boolean contienePalabrasProhibidas() {
        return PALABRAS_PROHIBIDAS.matcher(contenido).find();
    }

    public void like() {
        this.likes++;
    }

    public void unlike() {
        if (likes > 0) {
            likes--;
        }
    }

    public String getResumen() {
        if (contenido == null) {
            return "";
        }
        return contenido.length() <= 100
                ? contenido
                : contenido.substring(0, 100) + "...";
    }

    public String getTiempoDesdeCreacion() {
        LocalDateTime ahora = LocalDateTime.now();
        java.time.Duration duracion =
                java.time.Duration.between(fechaCreacion, ahora);

        if (duracion.toMinutes() < 1) return "hace unos segundos";
        if (duracion.toMinutes() < 60) return "hace " + duracion.toMinutes() + " minutos";
        if (duracion.toHours() < 24) return "hace " + duracion.toHours() + " horas";
        return "hace más de un día";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutor() {
        return autor != null ? autor : AUTOR_ANONIMO;
    }

    public void setAutor(String autor) {
        this.autor = (autor == null || autor.trim().isEmpty())
                ? AUTOR_ANONIMO
                : autor.trim().substring(0, Math.min(100, autor.trim().length()));
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        if (contenido == null) {
            throw new IllegalArgumentException("El comentario no puede ser nulo");
        }

        String limpio = contenido.trim();

        if (limpio.length() < 2) {
            throw new IllegalArgumentException(
                    "Comentario demasiado corto (mínimo 2 caracteres)");
        }

        if (limpio.length() > MAXIMO_CONTENIDO) {
            throw new IllegalArgumentException(
                    "Comentario excede el máximo de " + MAXIMO_CONTENIDO + " caracteres");
        }

        this.contenido = limpio;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getApunteId() {
        return apunteId;
    }

    public void setApunteId(String apunteId) {
        this.apunteId = apunteId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}