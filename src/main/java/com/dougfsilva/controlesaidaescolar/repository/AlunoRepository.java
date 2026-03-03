package com.dougfsilva.controlesaidaescolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dougfsilva.controlesaidaescolar.exceptions.ObjetoNaoEncontradoException;
import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.model.Turma;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

	default Aluno findByIdOrElseThrow(Long ID) {
		return findById(ID).orElseThrow(() -> 
		new ObjetoNaoEncontradoException(String.format("Aluno com id %d não encontrado!", ID)));
	}
	boolean existsByTurma(Turma turma);
}
