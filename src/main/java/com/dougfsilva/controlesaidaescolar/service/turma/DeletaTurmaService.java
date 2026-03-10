package com.dougfsilva.controlesaidaescolar.service.turma;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.model.Turma;
import com.dougfsilva.controlesaidaescolar.repository.AlunoRepository;
import com.dougfsilva.controlesaidaescolar.repository.TurmaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeletaTurmaService {

	private final TurmaRepository turmaRepository;
	private final AlunoRepository alunoRepository;
	private final SecurityUtils securityUtils;

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public void deletar(Long id) {
		Turma turma = turmaRepository.findByIdOrElseThrow(id);
		validarTurmaSemAlunos(turma);
		turmaRepository.delete(turma);
		log.info("Usuário [{}] excluiu a Turma {} com sucesso.", 
	             securityUtils.getUsernameUsuarioAtual(), 
	             id);	
		}

	private void validarTurmaSemAlunos(Turma turma) {
		if (alunoRepository.existsByTurma(turma)) {
			String.format("A turma '%s' não pode ser removida pois existem alunos matriculados nela.", turma.getNome());
		}
	}
}
