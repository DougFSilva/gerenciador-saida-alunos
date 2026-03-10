package com.dougfsilva.controlesaidaescolar.service.turma;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.dto.TurmaUpdateForm;
import com.dougfsilva.controlesaidaescolar.model.Turma;
import com.dougfsilva.controlesaidaescolar.repository.TurmaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EditaTurmaService {

	private final TurmaRepository repository;
	private final SecurityUtils securityUtils;
	private final TurmaValidator turmaValidator;
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@CacheEvict(value = "listaDeTurmas", allEntries = true)
	public Turma editar(Long id, TurmaUpdateForm form) {
		Turma turma = repository.findByIdOrElseThrow(id);
		if(!turma.getNome().equalsIgnoreCase(form.nome()) || turma.getAnoLetivo() != form.anoLetivo()) {
			turmaValidator.validarUnicidade(form.nome(), form.anoLetivo());
		}
		turma.setNome(form.nome());
		turma.setTurno(form.turno());
		turma.setAnoLetivo(form.anoLetivo());
		turma.setAtiva(form.ativa());
		Turma turmaEditada = repository.save(turma);
		log.info("Usuário [{}] editou a Turma {} com sucesso.", 
	             securityUtils.getUsernameUsuarioAtual(), 
	             turmaEditada.getId());
		return turmaEditada;
	}
	
}
