package com.mottu.fleet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevolucaoForm {
    
    @NotNull(message = "Pátio de devolução é obrigatório")
    private Long patioDevolucaoId;
    
    @Size(max = 1000, message = "Checklist de devolução deve ter no máximo 1000 caracteres")
    private String checklistDevolucao;
    
    private Long kmDevolucao;
}
