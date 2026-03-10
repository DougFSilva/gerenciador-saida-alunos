package com.dougfsilva.controlesaidaescolar.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dougfsilva.controlesaidaescolar.exceptions.ObjetoNaoEncontradoException;
import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.model.SaidaAluno;
import com.dougfsilva.controlesaidaescolar.model.StatusSaida;
import com.dougfsilva.controlesaidaescolar.model.Usuario;

@Repository
public interface SaidaAlunoRepository extends JpaRepository<SaidaAluno, Long> {

	default SaidaAluno findByIdOrElseThrow(Long ID) {
		return findById(ID).orElseThrow(
				() -> new ObjetoNaoEncontradoException(String.format("Saida com id %d não encontrada!", ID)));
	}
	
	Page<SaidaAluno> findByAluno(Aluno aluno, Pageable paginacao);
	
	List<SaidaAluno> findByAluno(Aluno aluno);
	
	Page<SaidaAluno> findByStatus(StatusSaida status, Pageable paginacao);
	
	Page<SaidaAluno> findBySolicitacaoDataHoraSolicitacaoBetween(LocalDateTime inicio, LocalDateTime fim, Pageable paginacao);

	boolean existsByAluno(Aluno aluno);

	boolean existsBySolicitacaoSolicitadaPor(Usuario solicitadoPor);

	boolean existsByAlunoAndStatusAndSolicitacaoDataHoraSolicitacaoBetween(Aluno aluno, StatusSaida status,
			LocalDateTime inicio, LocalDateTime fim);

}
