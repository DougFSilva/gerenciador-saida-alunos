package com.dougfsilva.controlesaidaescolar.service.saidaaluno;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.model.SaidaAluno;
import com.dougfsilva.controlesaidaescolar.model.StatusSaida;
import com.dougfsilva.controlesaidaescolar.repository.AlunoRepository;
import com.dougfsilva.controlesaidaescolar.repository.SaidaAlunoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BuscaSaidaAlunoService {

	private final SaidaAlunoRepository saidaAlunoRepository;
	private final AlunoRepository alunoRepository;
	
	@PreAuthorize("isAuthenticated()")
	public SaidaAluno buscarPeloId(Long id) {
		return saidaAlunoRepository.findByIdOrElseThrow(id);
	}
	
	@PreAuthorize("isAuthenticated()")
	public Page<SaidaAluno> buscarPeloAluno(Long alunoId, Pageable paginacao) {
		Aluno aluno = alunoRepository.findByIdOrElseThrow(alunoId);
		return saidaAlunoRepository.findByAluno(aluno, paginacao);
	}
	
	@PreAuthorize("isAuthenticated()")
	public Page<SaidaAluno> buscarPeloStatus(StatusSaida status, Pageable paginacao) {
		return saidaAlunoRepository.findByStatus(status, paginacao);
	}
	
	@PreAuthorize("isAuthenticated()")
	public Page<SaidaAluno> buscarPelaDataDeSolicitacao(LocalDateTime inicio, LocalDateTime fim, Pageable paginacao) {
		return saidaAlunoRepository.findBySolicitacaoDataHoraSolicitacaoBetween(inicio, fim, paginacao);
	}
	
	
}
