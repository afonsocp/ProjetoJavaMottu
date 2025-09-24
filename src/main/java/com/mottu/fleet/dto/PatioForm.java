package com.mottu.fleet.dto;

import com.mottu.fleet.domain.Patio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatioForm {
    
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;
    
    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    private String endereco;
    
    private Integer capacidadeOpcional;
    
    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    private String observacoes;
    
    public Patio toEntity() {
        Patio patio = new Patio();
        patio.setId(this.id);
        patio.setNome(this.nome);
        patio.setEndereco(this.endereco);
        patio.setCapacidadeOpcional(this.capacidadeOpcional);
        patio.setObservacoes(this.observacoes);
        return patio;
    }
    
    public static PatioForm fromEntity(Patio patio) {
        PatioForm form = new PatioForm();
        form.setId(patio.getId());
        form.setNome(patio.getNome());
        form.setEndereco(patio.getEndereco());
        form.setCapacidadeOpcional(patio.getCapacidadeOpcional());
        form.setObservacoes(patio.getObservacoes());
        return form;
    }
}
