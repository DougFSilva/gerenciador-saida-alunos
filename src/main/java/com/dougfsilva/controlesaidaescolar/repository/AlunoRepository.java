package com.dougfsilva.controlesaidaescolar.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dougfsilva.controlesaidaescolar.exceptions.ObjetoNaoEncontradoException;
import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.model.Turma;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

	default Aluno findByIdOrElseThrow(Long ID) {
		return findById(ID).orElseThrow(
				() -> new ObjetoNaoEncontradoException(String.format("Aluno com id %d não encontrado!", ID)));
	}

	Page<Aluno> findByTurma(Turma turma, Pageable paginacao);

	Optional<Aluno> findByMatricula(String matricula);

	boolean existsByMatricula(String matricula);

	boolean existsByTurma(Turma turma);
}
