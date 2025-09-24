package com.mottu.fleet.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "manutencoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manutencao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moto_id", nullable = false)
    @NotNull(message = "Moto é obrigatória")
    private Moto moto;
    
    @Column(nullable = false)
    private LocalDateTime abertoEm;
    
    @Column(nullable = false)
    private String abertoPor;
    
    @NotBlank(message = "Motivo é obrigatório")
    @Size(max = 200, message = "Motivo deve ter no máximo 200 caracteres")
    private String motivo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusManutencao status = StatusManutencao.ABERTA;
    
    private LocalDateTime fechadoEm;
    
    private String fechadoPor;
    
    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    private String observacoes;
    
    public enum StatusManutencao {
        ABERTA, FECHADA
    }
    
    public boolean isAberta() {
        return status == StatusManutencao.ABERTA;
    }
    
    public boolean isFechada() {
        return status == StatusManutencao.FECHADA;
    }
}
