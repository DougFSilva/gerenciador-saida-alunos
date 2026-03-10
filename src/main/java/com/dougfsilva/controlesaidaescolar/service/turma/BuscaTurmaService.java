package com.dougfsilva.controlesaidaescolar.service.turma;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.controlesaidaescolar.model.Turma;
import com.dougfsilva.controlesaidaescolar.repository.TurmaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BuscaTurmaService {
	
	private final TurmaRepository repository;
	
	@PreAuthorize("isAuthenticated()")
	public Turma buscarPeloId(Long id) {
		return repository.findByIdOrElseThrow(id);
	}
	
	@PreAuthorize("isAuthenticated()")
	public Page<Turma> buscarPeloNome(String nome, Pageable paginacao) {
		return repository.findByNomeContainingIgnoreCase(nome, paginacao);
	}
	
	@PreAuthorize("isAuthenticated()")
	public List<Turma> buscarPeloAnoLetivo(Integer anoLetivo) {
		return repository.findByAnoLetivoOrderByAnoLetivoDesc(anoLetivo);
	}
	
	@PreAuthorize("isAuthenticated()")
	public Page<Turma> buscarTodas(Pageable paginacao) {
		return repository.findAll(paginacao);
	}

}
