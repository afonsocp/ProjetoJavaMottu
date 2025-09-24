package com.mottu.fleet.repository;

import com.mottu.fleet.domain.Patio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatioRepository extends JpaRepository<Patio, Long> {
    
    @Query("SELECT p FROM Patio p WHERE " +
           "(:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')))")
    Page<Patio> findByFilters(@Param("nome") String nome, Pageable pageable);
}
