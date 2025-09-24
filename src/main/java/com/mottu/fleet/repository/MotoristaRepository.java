package com.mottu.fleet.repository;

import com.mottu.fleet.domain.Motorista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, Long> {
    
    Optional<Motorista> findByCpf(String cpf);
    
    boolean existsByCpf(String cpf);
    
    @Query("SELECT m FROM Motorista m WHERE " +
           "(:nome IS NULL OR LOWER(m.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
           "(:cpf IS NULL OR m.cpf LIKE CONCAT('%', :cpf, '%')) AND " +
           "(:ativo IS NULL OR m.ativo = :ativo)")
    Page<Motorista> findByFilters(@Param("nome") String nome, 
                                 @Param("cpf") String cpf, 
                                 @Param("ativo") Boolean ativo, 
                                 Pageable pageable);
    
    List<Motorista> findByAtivoTrue();
    
    @Query("SELECT m FROM Motorista m WHERE m.ativo = true AND m.validadeCnh > CURRENT_DATE ORDER BY m.nome")
    List<Motorista> findAtivosComCnhValida();
}
