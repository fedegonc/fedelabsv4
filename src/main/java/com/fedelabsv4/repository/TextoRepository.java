package com.fedelabsv4.repository;

import com.fedelabsv4.model.Texto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextoRepository extends JpaRepository<Texto, Long> {
}
