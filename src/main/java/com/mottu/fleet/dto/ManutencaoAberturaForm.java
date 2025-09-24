package com.mottu.fleet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManutencaoAberturaForm {
    
    @NotNull(message = "Moto é obrigatória")
    private Long motoId;
    
    @NotBlank(message = "Motivo é obrigatório")
    @Size(max = 200, message = "Motivo deve ter no máximo 200 caracteres")
    private String motivo;
}
