package com.fedelabsv4.repository;

import com.fedelabsv4.model.Apunte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApunteRepository extends JpaRepository<Apunte, Long> {
    Optional<Apunte> findBySlug(String slug);
}