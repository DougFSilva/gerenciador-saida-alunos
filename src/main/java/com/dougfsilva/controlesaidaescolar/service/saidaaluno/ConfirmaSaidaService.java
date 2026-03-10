package com.dougfsilva.controlesaidaescolar.service.saidaaluno;

import java.time.LocalDateTime;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.dto.ConfirmacaoSaidaAlunoForm;
import com.dougfsilva.controlesaidaescolar.model.ConfirmacaoSaida;
import com.dougfsilva.controlesaidaescolar.model.SaidaAluno;
import com.dougfsilva.controlesaidaescolar.model.StatusSaida;
import com.dougfsilva.controlesaidaescolar.model.Usuario;
import com.dougfsilva.controlesaidaescolar.repository.SaidaAlunoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConfirmaSaidaService {

	private final SaidaAlunoRepository saidaAlunoRepository;
	private final SecurityUtils securityUtils;
	
	@Transactional
	@PreAuthorize("isAuthenticated()")
	public SaidaAluno confirmar(ConfirmacaoSaidaAlunoForm form) {
		SaidaAluno saidaAluno = saidaAlunoRepository.findByIdOrElseThrow(form.saidaAlunoId());
		Usuario usuarioAtual = securityUtils.getUsuarioAtual();
		ConfirmacaoSaida confirmacaoSaida = new ConfirmacaoSaida(LocalDateTime.now(), usuarioAtual, form.observacao());
		saidaAluno.setConfirmacao(confirmacaoSaida);
		saidaAluno.setStatus(StatusSaida.CONFIRMADA);
		SaidaAluno saidaAlunoAtualizada = saidaAlunoRepository.save(saidaAluno);
		log.info("Saída confirmada - Aluno: {} | Responsável: {}", saidaAlunoAtualizada.getAluno().getId(), usuarioAtual.getUsername());
		return saidaAlunoAtualizada;
	}
}
