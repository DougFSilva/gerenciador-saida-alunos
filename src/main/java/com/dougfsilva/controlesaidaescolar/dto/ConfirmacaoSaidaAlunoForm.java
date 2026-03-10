package com.dougfsilva.controlesaidaescolar.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConfirmacaoSaidaAlunoForm(
		
		@NotNull(message = "O ID da saidaAluno é obrigatório")
		Long saidaAlunoId,
        
        @Size(max = 255, message = "A observação deve ter no máximo 255 caracteres")
        String observacao
		
		) {

}
