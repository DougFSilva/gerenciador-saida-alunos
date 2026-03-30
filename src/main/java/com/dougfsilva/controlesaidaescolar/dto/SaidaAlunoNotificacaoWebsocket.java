package com.dougfsilva.controlesaidaescolar.dto;

import java.time.LocalDateTime;

import com.dougfsilva.controlesaidaescolar.model.StatusSaida;

public record SaidaAlunoNotificacaoWebsocket (
		Long id,
		Long alunoId,
		String nomeAluno,
		String foto,
		String turma,
		StatusSaida status,
		LocalDateTime dataHora
		
		) {

}
