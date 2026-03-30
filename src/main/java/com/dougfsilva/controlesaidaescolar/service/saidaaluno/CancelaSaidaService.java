package com.dougfsilva.controlesaidaescolar.service.saidaaluno;

import java.time.LocalDateTime;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.dto.CancelamentoSaidaAlunoForm;
import com.dougfsilva.controlesaidaescolar.model.CancelamentoSaida;
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
public class CancelaSaidaService {

	private final SaidaAlunoRepository saidaAlunoRepository;
	private final SecurityUtils securityUtils;
	private final EnviaNotificacaoWebsocketService notificacaoWebsocket;
	
	@Transactional
	@PreAuthorize("isAuthenticated()")
	public SaidaAluno cancelar(CancelamentoSaidaAlunoForm form) {
		SaidaAluno saidaAluno = saidaAlunoRepository.findByIdOrElseThrow(form.saidaAlunoId());
		Usuario usuarioAtual = securityUtils.getUsuarioAtual();
		CancelamentoSaida cancelamentoSaida = new CancelamentoSaida(LocalDateTime.now(), usuarioAtual, form.observacao());
		saidaAluno.setCancelamento(cancelamentoSaida);
		saidaAluno.setStatus(StatusSaida.CANCELADA);
		SaidaAluno saidaAlunoAtualizada = saidaAlunoRepository.save(saidaAluno);
		log.info("Saída cancelada - Aluno: {} | Responsável: {}", saidaAlunoAtualizada.getAluno().getId(), usuarioAtual.getUsername());
		notificacaoWebsocket.enviar(saidaAlunoAtualizada);
		return saidaAlunoAtualizada;
	}
	
}
