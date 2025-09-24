package com.mottu.fleet.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "alocacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alocacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moto_id", nullable = false)
    @NotNull(message = "Moto é obrigatória")
    private Moto moto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motorista_id", nullable = false)
    @NotNull(message = "Motorista é obrigatório")
    private Motorista motorista;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patio_origem_id", nullable = false)
    @NotNull(message = "Pátio de origem é obrigatório")
    private Patio patioOrigem;
    
    @Column(nullable = false)
    private LocalDateTime dataHoraSaida;
    
    @Column(columnDefinition = "TEXT")
    private String checklistSaida;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAlocacao status = StatusAlocacao.ATIVA;
    
    private LocalDateTime dataHoraDevolucao;
    
    @Column(columnDefinition = "TEXT")
    private String checklistDevolucao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patio_devolucao_id")
    private Patio patioDevolucao;
    
    private Long kmDevolucao;
    
    public enum StatusAlocacao {
        ATIVA, ENCERRADA
    }
    
    public boolean isAtiva() {
        return status == StatusAlocacao.ATIVA;
    }
    
    public boolean isEncerrada() {
        return status == StatusAlocacao.ENCERRADA;
    }
}
