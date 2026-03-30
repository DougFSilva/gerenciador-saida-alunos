package com.dougfsilva.controlesaidaescolar.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SolicitacaoSaidaAlunoForm(
		
		@NotNull(message = "O ID do aluno é obrigatório.")
        Long alunoId,
        
        @Size(max = 255, message = "A observação deve ter no máximo 255 caracteres.")
        String observacao
		
		) {

}
