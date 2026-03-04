package com.dougfsilva.controlesaidaescolar.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dougfsilva.controlesaidaescolar.exceptions.ObjetoNaoEncontradoException;
import com.dougfsilva.controlesaidaescolar.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

	default Turma findByIdOrElseThrow(Long ID) {
		return findById(ID).orElseThrow(
				() -> new ObjetoNaoEncontradoException(String.format("Turma com id %d não encontrada!", ID)));
	}

	boolean existsByNomeAndAnoLetivo(String nome, Integer anoLetivo);

	boolean existsByNomeAndAnoLetivoAndIdNot(String nome, String anoLetivo, Long id);

	Page<Turma> findByNomeContainingIgnoreCase(String nome);

	List<Turma> findByAnoLetivoOrderByAnoLetivoDesc(Integer anoLetivo);
	
}
