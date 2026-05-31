package com.fedelabsv4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fedelabsv4.model.Comentario;

@Repository
public interface ComentarioRepository
        extends JpaRepository<Comentario, Long> {

    List<Comentario> findByApunteId(Long apunteId);

}