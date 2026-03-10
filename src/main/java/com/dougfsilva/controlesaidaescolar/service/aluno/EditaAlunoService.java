package com.dougfsilva.controlesaidaescolar.service.aluno;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.dto.AlunoUpdateForm;
import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.model.Turma;
import com.dougfsilva.controlesaidaescolar.repository.AlunoRepository;
import com.dougfsilva.controlesaidaescolar.repository.TurmaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EditaAlunoService {

	private final AlunoRepository alunoRepository;
	private final TurmaRepository turmaRepository;
	private final SecurityUtils securityUtils;
	private final AlunoValidator alunoValidator;

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public Aluno editar(Long id, AlunoUpdateForm form) {
		Aluno aluno = alunoRepository.findByIdOrElseThrow(id);
		if (!aluno.getMatricula().equalsIgnoreCase(form.matricula())) {
			alunoValidator.validarUnicidadeMatricula(form.matricula());
		}
		Turma turma = turmaRepository.findByIdOrElseThrow(form.turmaId());
		aluno.setAtivo(form.ativo());
		aluno.setDataNascimento(form.dataNascimento());
		aluno.setMatricula(form.matricula());
		aluno.setNome(form.nome());
		aluno.setTurma(turma);
		Aluno alunoEditado = alunoRepository.save(aluno);
		log.info("Usuário [{}] editou a Aluno {} com sucesso.", securityUtils.getUsernameUsuarioAtual(), alunoEditado.getId());
		return alunoEditado;

	}

}
