package com.dougfsilva.controlesaidaescolar.service.aluno;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.dto.AlunoForm;
import com.dougfsilva.controlesaidaescolar.exceptions.RegistroDuplicadoException;
import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.model.Turma;
import com.dougfsilva.controlesaidaescolar.repository.AlunoRepository;
import com.dougfsilva.controlesaidaescolar.repository.TurmaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CriaAlunoService {
	
	private final AlunoRepository alunoRepository;
	private final TurmaRepository turmaRepository;
	private final SecurityUtils securityUtils;
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public Aluno criar(AlunoForm form) {
		validarUnicaMatricula(form.matricula());
		Turma turma = turmaRepository.findByIdOrElseThrow(form.turmaId());
		Aluno aluno = new Aluno(form.matricula(), form.nome(), form.dataNascimento(), turma);
		Aluno alunoCriado = alunoRepository.save(aluno);
		log.info("Usuário [{}] ativou a Turma {} com sucesso.", 
	             securityUtils.getUsuarioAtual(), 
	             alunoCriado.getId());			
		return alunoCriado;

	}
	
	private void validarUnicaMatricula(String matricula) {
		if (alunoRepository.existsByMatricula(matricula)) {
			throw new RegistroDuplicadoException("Aluno com matrícula %s já existente na base de dados");
		}
	}
}
