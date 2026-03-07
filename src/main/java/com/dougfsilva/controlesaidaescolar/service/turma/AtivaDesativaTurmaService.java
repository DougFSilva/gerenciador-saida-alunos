package com.dougfsilva.controlesaidaescolar.service.turma;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.model.Turma;
import com.dougfsilva.controlesaidaescolar.repository.TurmaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AtivaDesativaTurmaService {

	private final TurmaRepository repository;
	private final SecurityUtils securityUtils;
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public void ativarTurma(Long id) {
		Turma turma = repository.findByIdOrElseThrow(id);
		turma.setAtiva(true);
		Turma turmaAtivada = repository.save(turma);
		log.info("Usuário [{}] ativou a Turma {} com sucesso.", 
	             securityUtils.getUsuarioAtual(), 
	             turmaAtivada.getId());	
		}
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public void desativarTurma(Long id) {
		Turma turma = repository.findByIdOrElseThrow(id);
		turma.setAtiva(false);
		Turma turmaAtivada = repository.save(turma);
		log.info("Usuário [{}] desativou a Turma {} com sucesso.", 
	             securityUtils.getUsuarioAtual(), 
	             turmaAtivada.getId());		}
}
