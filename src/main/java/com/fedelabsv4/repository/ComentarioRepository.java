package com.fedelabsv4.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fedelabsv4.model.Comentario;

@Repository
public interface ComentarioRepository
        extends MongoRepository<Comentario, String> {

    List<Comentario> findByApunteId(String apunteId);

}