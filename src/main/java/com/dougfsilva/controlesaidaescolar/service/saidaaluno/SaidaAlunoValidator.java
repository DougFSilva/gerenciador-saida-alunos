package com.dougfsilva.controlesaidaescolar.service.saidaaluno;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.dougfsilva.controlesaidaescolar.exceptions.RegraDeNegocioException;
import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.model.StatusSaida;
import com.dougfsilva.controlesaidaescolar.repository.SaidaAlunoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SaidaAlunoValidator {

	private final SaidaAlunoRepository saidaAlunoRepository;

	public void validarNenhumaOutraSaidaSolicitadaNoDia(Aluno aluno) {
		LocalDateTime inicioDia = LocalDateTime.now().with(LocalTime.MIN);
		LocalDateTime fimDia = LocalDateTime.now().with(LocalTime.MAX);
		if (saidaAlunoRepository.existsByAlunoAndStatusAndSolicitacaoDataHoraSolicitacaoBetween(aluno,
				StatusSaida.SOLICITADA, inicioDia, fimDia)) {
			throw new RegraDeNegocioException(String
					.format("O aluno %s já possui uma solicitação de saída registrada para hoje.", aluno.getId()));
		}
	}
}
