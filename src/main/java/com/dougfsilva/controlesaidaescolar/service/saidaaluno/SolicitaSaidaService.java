package com.dougfsilva.controlesaidaescolar.service.saidaaluno;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.dto.SolicitacaoSaidaAlunoForm;
import com.dougfsilva.controlesaidaescolar.exceptions.RegraDeNegocioException;
import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.model.SaidaAluno;
import com.dougfsilva.controlesaidaescolar.model.SolicitacaoSaida;
import com.dougfsilva.controlesaidaescolar.model.StatusSaida;
import com.dougfsilva.controlesaidaescolar.model.Usuario;
import com.dougfsilva.controlesaidaescolar.repository.AlunoRepository;
import com.dougfsilva.controlesaidaescolar.repository.SaidaAlunoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SolicitaSaidaService {

	private final SaidaAlunoRepository saidaAlunoRepository;
	private final AlunoRepository alunoRepository;
	private final SecurityUtils securityUtils;

	@Transactional
	@PreAuthorize("isAuthenticated()")
	public SaidaAluno solicitar(SolicitacaoSaidaAlunoForm form) {
		Aluno aluno = alunoRepository.findByIdOrElseThrow(form.alunoId());
		validarNenhumaOutraSaidaSolicitadaNoDia(aluno);
		Usuario usuarioAtual = securityUtils.getUsuarioAtual();
		SolicitacaoSaida solicitacao = new SolicitacaoSaida(LocalDateTime.now(), usuarioAtual, form.observacao());
		SaidaAluno saidaAluno = new SaidaAluno(aluno, StatusSaida.SOLICITADA, solicitacao);
		SaidaAluno saidaAlunoCriada = saidaAlunoRepository.save(saidaAluno);
		log.info("Saída confirmada - Aluno: {} | Responsável: {}", aluno.getId(), usuarioAtual.getUsername());
		return saidaAlunoCriada;
	}
	
	private void validarNenhumaOutraSaidaSolicitadaNoDia(Aluno aluno) {
		LocalDateTime inicioDia = LocalDateTime.now().with(LocalTime.MIN);
		LocalDateTime fimDia = LocalDateTime.now().with(LocalTime.MAX);
		if (saidaAlunoRepository.existsByAlunoAndStatusAndSolicitacaoDataHoraSolicitacaoBetween(aluno,
				StatusSaida.SOLICITADA, inicioDia, fimDia)) {
			throw new RegraDeNegocioException(String
					.format("O aluno %s já possui uma solicitação de saída registrada para hoje.", aluno.getId()));
		}
	}

}
