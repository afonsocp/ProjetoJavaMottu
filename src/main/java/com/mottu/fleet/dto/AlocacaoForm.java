package com.mottu.fleet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlocacaoForm {
    
    @NotNull(message = "Moto é obrigatória")
    private Long motoId;
    
    @NotNull(message = "Motorista é obrigatório")
    private Long motoristaId;
    
    @NotNull(message = "Pátio de origem é obrigatório")
    private Long patioOrigemId;
    
    @Size(max = 1000, message = "Checklist de saída deve ter no máximo 1000 caracteres")
    private String checklistSaida;
}
