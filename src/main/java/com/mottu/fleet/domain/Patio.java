package com.mottu.fleet.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;
    
    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String endereco;
    
    private Integer capacidadeOpcional;
    
    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    private String observacoes;
    
    @OneToMany(mappedBy = "patioOrigem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alocacao> alocacoesOrigem = new ArrayList<>();
    
    @OneToMany(mappedBy = "patioDevolucao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alocacao> alocacoesDevolucao = new ArrayList<>();
}
