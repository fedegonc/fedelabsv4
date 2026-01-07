package com.fedelabsv4.repository;

import com.fedelabsv4.dominio.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByOrderByUpdatedAtDesc();
    Optional<Project> findBySlug(String slug);
}
