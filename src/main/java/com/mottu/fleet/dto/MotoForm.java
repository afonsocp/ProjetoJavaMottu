package com.mottu.fleet.dto;

import com.mottu.fleet.domain.Moto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotoForm {
    
    private Long id;
    
    @NotBlank(message = "Placa é obrigatória")
    @Pattern(regexp = "^[A-Z]{3}[0-9]{4}$", message = "Placa deve estar no formato ABC1234")
    private String placa;
    
    @NotBlank(message = "Modelo é obrigatório")
    @Size(max = 100, message = "Modelo deve ter no máximo 100 caracteres")
    private String modelo;
    
    @Min(value = 2000, message = "Ano deve ser maior ou igual a 2000")
    @Max(value = 2030, message = "Ano deve ser menor ou igual a 2030")
    private Integer ano;
    
    @Min(value = 0, message = "Quilometragem atual deve ser maior ou igual a 0")
    private Long kmAtual = 0L;
    
    private Moto.StatusMoto status = Moto.StatusMoto.DISPONIVEL;
    
    @Size(max = 50, message = "Chassi deve ter no máximo 50 caracteres")
    private String chassi;
    
    @Size(max = 20, message = "Renavam deve ter no máximo 20 caracteres")
    private String renavam;
    
    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    private String observacoes;
    
    public Moto toEntity() {
        Moto moto = new Moto();
        moto.setId(this.id);
        moto.setPlaca(this.placa);
        moto.setModelo(this.modelo);
        moto.setAno(this.ano);
        moto.setKmAtual(this.kmAtual);
        moto.setStatus(this.status);
        moto.setChassi(this.chassi);
        moto.setRenavam(this.renavam);
        moto.setObservacoes(this.observacoes);
        return moto;
    }
    
    public static MotoForm fromEntity(Moto moto) {
        MotoForm form = new MotoForm();
        form.setId(moto.getId());
        form.setPlaca(moto.getPlaca());
        form.setModelo(moto.getModelo());
        form.setAno(moto.getAno());
        form.setKmAtual(moto.getKmAtual());
        form.setStatus(moto.getStatus());
        form.setChassi(moto.getChassi());
        form.setRenavam(moto.getRenavam());
        form.setObservacoes(moto.getObservacoes());
        return form;
    }
}
