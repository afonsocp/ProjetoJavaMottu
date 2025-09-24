package com.mottu.fleet.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManutencaoFechamentoForm {
    
    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    private String observacoes;
}
