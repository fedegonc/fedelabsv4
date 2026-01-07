package com.fedelabsv4.repository;

import com.fedelabsv4.dominio.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<Idea> findAllByOrderByUpdatedAtDesc();
    List<Idea> findByStatusOrderByUpdatedAtDesc(String status);
}
