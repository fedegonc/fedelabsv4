package com.fedelabsv4.repository;

import com.fedelabsv4.model.Apunte;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApunteRepository extends MongoRepository<Apunte, String> {

    Optional<Apunte> findBySlug(String slug);

}