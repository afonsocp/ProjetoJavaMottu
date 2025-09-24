package com.mottu.fleet.dto;

import com.mottu.fleet.domain.Motorista;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotoristaForm {
    
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;
    
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^[0-9]{11}$", message = "CPF deve conter apenas 11 dígitos")
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
    
    private Boolean ativo = true;
    
    public Motorista toEntity() {
        Motorista motorista = new Motorista();
        motorista.setId(this.id);
        motorista.setNome(this.nome);
        motorista.setCpf(this.cpf);
        motorista.setCnh(this.cnh);
        motorista.setValidadeCnh(this.validadeCnh);
        motorista.setTelefone(this.telefone);
        motorista.setEmail(this.email);
        motorista.setAtivo(this.ativo);
        return motorista;
    }
    
    public static MotoristaForm fromEntity(Motorista motorista) {
        MotoristaForm form = new MotoristaForm();
        form.setId(motorista.getId());
        form.setNome(motorista.getNome());
        form.setCpf(motorista.getCpf());
        form.setCnh(motorista.getCnh());
        form.setValidadeCnh(motorista.getValidadeCnh());
        form.setTelefone(motorista.getTelefone());
        form.setEmail(motorista.getEmail());
        form.setAtivo(motorista.getAtivo());
        return form;
    }
}
