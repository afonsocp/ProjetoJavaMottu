package com.mottu.fleet.repository;

import com.mottu.fleet.domain.Moto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {
    
    Optional<Moto> findByPlaca(String placa);
    
    boolean existsByPlaca(String placa);
    
    @Query("SELECT m FROM Moto m WHERE " +
           "(:placa IS NULL OR LOWER(m.placa) LIKE LOWER(CONCAT('%', :placa, '%'))) AND " +
           "(:modelo IS NULL OR LOWER(m.modelo) LIKE LOWER(CONCAT('%', :modelo, '%'))) AND " +
           "(:status IS NULL OR m.status = :status)")
    Page<Moto> findByFilters(@Param("placa") String placa, 
                            @Param("modelo") String modelo, 
                            @Param("status") Moto.StatusMoto status, 
                            Pageable pageable);
    
    List<Moto> findByStatus(Moto.StatusMoto status);
    
    long countByStatus(Moto.StatusMoto status);
    
    @Query("SELECT m FROM Moto m WHERE m.status = 'DISPONIVEL' ORDER BY m.placa")
    List<Moto> findDisponiveis();
}
