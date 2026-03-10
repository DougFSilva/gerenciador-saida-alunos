package com.dougfsilva.controlesaidaescolar.service.turma;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.dto.TurmaForm;
import com.dougfsilva.controlesaidaescolar.model.Turma;
import com.dougfsilva.controlesaidaescolar.repository.TurmaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CriaTurmaService {

	private final TurmaRepository repository;
	private final SecurityUtils securityUtils;
	private final TurmaValidator turmaValidator;

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public Turma criar(TurmaForm form) {
		turmaValidator.validarUnicidade(form.nome(), form.anoLetivo());
		Turma turma = new Turma(null, form.nome(), form.turno(), form.anoLetivo(), true);
		Turma turmaCriada = repository.save(turma);
		log.info("Usuário [{}] criou a turma com sucesso! ID: {}", 
	             securityUtils.getUsernameUsuarioAtual(), 
	             turmaCriada.getId());
		return turmaCriada;
	}

}
