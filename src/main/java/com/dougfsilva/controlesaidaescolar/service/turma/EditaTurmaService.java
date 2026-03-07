package com.dougfsilva.controlesaidaescolar.service.turma;

import org.springframework.security.access.prepost.PreAuthorize;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.dto.TurmaUpdateForm;
import com.dougfsilva.controlesaidaescolar.model.Turma;
import com.dougfsilva.controlesaidaescolar.repository.TurmaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class EditaTurmaService {

	private final TurmaRepository repository;
	private final SecurityUtils securityUtils;
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public Turma editar(TurmaUpdateForm form) {
		Turma turma = repository.findByIdOrElseThrow(form.id());
		turma.setNome(form.nome());
		turma.setTurno(form.turno());
		turma.setAnoLetivo(form.anoLetivo());
		turma.setAtiva(form.ativa());
		Turma turmaEditada = repository.save(turma);
		log.info("Usuário [{}] editou a Turma {} com sucesso.", 
	             securityUtils.getUsuarioAtual(), 
	             turmaEditada.getId());
		return turmaEditada;
	}
}
