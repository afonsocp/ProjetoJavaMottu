package com.mottu.fleet.repository;

import com.mottu.fleet.domain.Alocacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlocacaoRepository extends JpaRepository<Alocacao, Long> {
    
    @Query("SELECT a FROM Alocacao a WHERE a.moto.id = :motoId AND a.status = 'ATIVA'")
    Optional<Alocacao> findAtivaByMotoId(@Param("motoId") Long motoId);
    
    @Query("SELECT a FROM Alocacao a WHERE a.motorista.id = :motoristaId AND a.status = 'ATIVA'")
    Optional<Alocacao> findAtivaByMotoristaId(@Param("motoristaId") Long motoristaId);
    
    @Query("SELECT a FROM Alocacao a WHERE a.status = 'ATIVA'")
    List<Alocacao> findAllAtivas();
    
    @Query("SELECT a FROM Alocacao a WHERE " +
           "(:motoId IS NULL OR a.moto.id = :motoId) AND " +
           "(:motoristaId IS NULL OR a.motorista.id = :motoristaId) AND " +
           "(:status IS NULL OR a.status = :status)")
    Page<Alocacao> findByFilters(@Param("motoId") Long motoId, 
                                @Param("motoristaId") Long motoristaId, 
                                @Param("status") Alocacao.StatusAlocacao status, 
                                Pageable pageable);
}
