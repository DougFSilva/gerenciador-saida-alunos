package com.dougfsilva.controlesaidaescolar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CancelamentoSaidaAlunoForm(
		
		@NotNull(message = "O ID da saidaAluno é obrigatório.")
		Long saidaAlunoId,
        
        @Size(max = 255, message = "A observação deve ter no máximo 255 caracteres.")
		@NotBlank(message = "A observação de cancelamento é obrigatória.")
        String observacao
		
		) {

}
