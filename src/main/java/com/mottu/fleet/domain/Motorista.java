package com.mottu.fleet.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "motoristas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Motorista {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;
    
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^[0-9]{11}$", message = "CPF deve conter apenas 11 dígitos")
    @Column(unique = true, nullable = false)
    private String cpf;
    
    @NotBlank(message = "CNH é obrigatória")
    @Size(max = 20, message = "CNH deve ter no máximo 20 caracteres")
    private String cnh;
    
    @NotNull(message = "Validade da CNH é obrigatória")
    @Future(message = "Validade da CNH deve ser futura")
    private LocalDate validadeCnh;
    
    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;
    
    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;
    
    @Column(nullable = false)
    private Boolean ativo = true;
    
    @OneToMany(mappedBy = "motorista", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alocacao> alocacoes = new ArrayList<>();
    
    public boolean isAtivo() {
        return ativo != null && ativo;
    }
    
    public boolean isCnhValida() {
        return validadeCnh != null && validadeCnh.isAfter(LocalDate.now());
    }
}
