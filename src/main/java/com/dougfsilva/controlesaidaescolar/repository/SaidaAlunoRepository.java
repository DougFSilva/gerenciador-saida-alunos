package com.dougfsilva.controlesaidaescolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dougfsilva.controlesaidaescolar.exceptions.ObjetoNaoEncontradoException;
import com.dougfsilva.controlesaidaescolar.model.SaidaAluno;

public interface SaidaAlunoRepository extends JpaRepository<SaidaAluno, Long> {

	default SaidaAluno findByIdOrElseThrow(Long ID) {
		return findById(ID).orElseThrow(() -> 
		new ObjetoNaoEncontradoException(String.format("Saida com id %d não encontrada!", ID)));
	}
}
