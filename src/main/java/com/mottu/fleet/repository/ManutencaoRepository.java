package com.mottu.fleet.repository;

import com.mottu.fleet.domain.Manutencao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    
    @Query("SELECT m FROM Manutencao m WHERE m.moto.id = :motoId AND m.status = 'ABERTA'")
    Optional<Manutencao> findAbertaByMotoId(@Param("motoId") Long motoId);
    
    @Query("SELECT m FROM Manutencao m WHERE m.status = 'ABERTA'")
    List<Manutencao> findAllAbertas();
    
    @Query("SELECT m FROM Manutencao m WHERE " +
           "(:motoId IS NULL OR m.moto.id = :motoId) AND " +
           "(:status IS NULL OR m.status = :status)")
    Page<Manutencao> findByFilters(@Param("motoId") Long motoId, 
                                  @Param("status") Manutencao.StatusManutencao status, 
                                  Pageable pageable);
    
    long countByStatus(Manutencao.StatusManutencao status);
}
