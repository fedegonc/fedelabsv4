package com.fedelabsv4.repository;

import com.fedelabsv4.dominio.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPublishedTrueOrderByPublishedAtDesc();
    List<Post> findByPublishedTrueOrderByPublishedAtDesc(java.time.LocalDateTime before);
    Optional<Post> findBySlugAndPublishedTrue(String slug);
}
