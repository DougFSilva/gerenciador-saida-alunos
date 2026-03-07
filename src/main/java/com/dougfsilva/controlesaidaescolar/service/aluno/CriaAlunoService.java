package com.dougfsilva.controlesaidaescolar.service.aluno;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.dto.AlunoForm;
import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.model.Turma;
import com.dougfsilva.controlesaidaescolar.repository.AlunoRepository;
import com.dougfsilva.controlesaidaescolar.repository.TurmaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CriaAlunoService {
	
	private final AlunoRepository alunoRepository;
	private final TurmaRepository turmaRepository;
	private final SecurityUtils securityUtils;
	private final AlunoValidator alunoValidator;
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public Aluno criar(AlunoForm form) {
		alunoValidator.validarUnicidadeMatricula(form.matricula());
		Turma turma = turmaRepository.findByIdOrElseThrow(form.turmaId());
		Aluno aluno = new Aluno(form.matricula(), form.nome(), form.dataNascimento(), turma);
		Aluno alunoCriado = alunoRepository.save(aluno);
		log.info("Usuário [{}] criou o Aluno {} com sucesso.", 
	             securityUtils.getUsuarioAtual(), 
	             alunoCriado.getId());			
		return alunoCriado;

	}
	
	
}
