package com.mottu.fleet.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "motos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Moto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Placa é obrigatória")
    @Pattern(regexp = "^[A-Z]{3}[0-9]{4}$", message = "Placa deve estar no formato ABC1234")
    @Column(unique = true, nullable = false)
    private String placa;
    
    @NotBlank(message = "Modelo é obrigatório")
    @Size(max = 100, message = "Modelo deve ter no máximo 100 caracteres")
    private String modelo;
    
    @Min(value = 2000, message = "Ano deve ser maior ou igual a 2000")
    @Max(value = 2030, message = "Ano deve ser menor ou igual a 2030")
    private Integer ano;
    
    @Min(value = 0, message = "Quilometragem atual deve ser maior ou igual a 0")
    private Long kmAtual = 0L;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMoto status = StatusMoto.DISPONIVEL;
    
    @Size(max = 50, message = "Chassi deve ter no máximo 50 caracteres")
    private String chassi;
    
    @Size(max = 20, message = "Renavam deve ter no máximo 20 caracteres")
    private String renavam;
    
    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    private String observacoes;
    
    @OneToMany(mappedBy = "moto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alocacao> alocacoes = new ArrayList<>();
    
    @OneToMany(mappedBy = "moto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Manutencao> manutencoes = new ArrayList<>();
    
    public enum StatusMoto {
        DISPONIVEL, ALOCADA, MANUTENCAO, INATIVA
    }
    
    public boolean isDisponivel() {
        return status == StatusMoto.DISPONIVEL;
    }
    
    public boolean isAlocada() {
        return status == StatusMoto.ALOCADA;
    }
    
    public boolean isEmManutencao() {
        return status == StatusMoto.MANUTENCAO;
    }
}
