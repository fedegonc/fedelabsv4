package com.fedelabsv4.service;

import com.fedelabsv4.model.Apunte;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ApunteService {

    private final List<Apunte> apuntes = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public ApunteService() {
        String loremShort = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        String loremLong = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.";

        String[][] data = {
                {"La belleza del minimalismo", "Diseño", loremLong},
                {"Spring Boot en producción", "Tecnología", loremLong},
                {"Patrones de arquitectura limpia", "Programación", loremShort},
                {"Reflexiones sobre la creatividad", "Personal", loremShort},
                {"Tipografía y jerarquía visual", "Diseño", loremLong},
                {"APIs RESTful bien diseñadas", "Programación", loremLong},
                {"El poder del espacio negativo", "Diseño", loremShort},
                {"Notas sobre productividad", "Personal", loremShort},
                {"Microservicios en Java", "Tecnología", loremLong},
                {"Color y composición", "Diseño", loremLong},
                {"Testing efectivo en backend", "Programación", loremShort},
                {"Hábitos de un desarrollador", "Personal", loremLong},
                {"Diseño de sistemas escalables", "Tecnología", loremLong},
                {"Lecturas que cambiaron mi forma de pensar", "Personal", loremShort},
                {"Grids y layouts modernos en CSS", "Diseño", loremLong},
                {"Principios SOLID aplicados", "Programación", loremLong},
                {"La importancia del descanso", "Personal", loremShort},
                {"Docker para desarrolladores Java", "Tecnología", loremShort},
                {"Branding personal en la web", "Negocios", loremLong},
                {"Estrategias de caché en aplicaciones", "Tecnología", loremLong},
                {"Cómo dar feedback efectivo", "Personal", loremShort},
                {"Diseño centrado en el usuario", "Diseño", loremLong},
                {"Construyendo MVPs rápidamente", "Negocios", loremShort},
                {"Concurrencia y paralelismo en Java", "Programación", loremLong},
                {"Sobre la disciplina diaria", "Personal", loremShort},
                {"Animaciones sutiles en interfaces", "Diseño", loremLong},
                {"Bases de datos relacionales vs NoSQL", "Tecnología", loremLong},
                {"El arte de simplificar el código", "Programación", loremShort},
                {"Cómo cobrar lo que vales", "Negocios", loremLong},
                {"Mi setup de desarrollo en 2026", "Tecnología", loremShort},
                {"Mood boards y referencias visuales", "Diseño", loremShort},
                {"Documentar antes de construir", "Programación", loremLong},
                {"Aprender en público", "Personal", loremShort},
                {"Optimización de consultas SQL", "Programación", loremLong},
                {"Pricing estratégico para freelancers", "Negocios", loremShort},
                {"Formularios accesibles", "Diseño", loremLong},
                {"Lecciones de side projects", "Personal", loremLong}
        };

        for (int i = 0; i < data.length; i++) {
            apuntes.add(new Apunte(
                    idCounter.getAndIncrement(),
                    data[i][0],
                    data[i][2],
                    data[i][1],
                    "https://picsum.photos/seed/apunte" + (i + 1) + "/800/1000"));
        }
    }

    public List<Apunte> obtenerTodos() {
        return new ArrayList<>(apuntes);
    }

    public Optional<Apunte> obtenerPorId(Long id) {
        return apuntes.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    public Apunte crear(Apunte apunte) {
        apunte.setId(idCounter.getAndIncrement());
        apuntes.add(apunte);
        return apunte;
    }

    public Optional<Apunte> actualizar(Long id, Apunte apunteActualizado) {
        return obtenerPorId(id).map(apunte -> {
            apunte.setTitulo(apunteActualizado.getTitulo());
            apunte.setContenido(apunteActualizado.getContenido());
            apunte.setCategoria(apunteActualizado.getCategoria());
            apunte.setImageUrl(apunteActualizado.getImageUrl());
            return apunte;
        });
    }

    public boolean eliminar(Long id) {
        return apuntes.removeIf(a -> a.getId().equals(id));
    }
}
