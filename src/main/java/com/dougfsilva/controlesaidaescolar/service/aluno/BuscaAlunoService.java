package com.dougfsilva.controlesaidaescolar.service.aluno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.controlesaidaescolar.exceptions.ObjetoNaoEncontradoException;
import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.model.Turma;
import com.dougfsilva.controlesaidaescolar.repository.AlunoRepository;
import com.dougfsilva.controlesaidaescolar.repository.TurmaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BuscaAlunoService {

	private final AlunoRepository alunoRepository;
	private final TurmaRepository turmaRepository;

	@PreAuthorize("isAuthenticated()")
	public Aluno buscarPeloId(Long id) {
		return alunoRepository.findByIdOrElseThrow(id);
	}

	@PreAuthorize("isAuthenticated()")
	public Aluno buscarPelaMatricula(String matricula) {
		return alunoRepository.findByMatricula(matricula).orElseThrow(() -> new ObjetoNaoEncontradoException(
				String.format("Aluno com matrícula '%s' não encontrado", matricula)));
	}
	
	@PreAuthorize("isAuthenticated()")
	public Page<Aluno> buscarPelaTurma(Long turmaId, Pageable paginacao) {
		Turma turma = turmaRepository.findByIdOrElseThrow(turmaId);
		return alunoRepository.findByTurma(turma, paginacao);
	}
	
	@PreAuthorize("isAuthenticated()")
	public Page<Aluno> buscarPeloNome(String nome, Pageable paginacao) {
		return alunoRepository.findByNomeContainingIgnoreCase(nome, paginacao);
	}
	
	@PreAuthorize("isAuthenticated()")
	public Page<Aluno> buscarTodos(Pageable paginacao) {
		return alunoRepository.findAll(paginacao);
	}
}
