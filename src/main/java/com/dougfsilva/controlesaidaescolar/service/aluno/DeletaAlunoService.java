package com.dougfsilva.controlesaidaescolar.service.aluno;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.exceptions.EntidadeEmUsoException;
import com.dougfsilva.controlesaidaescolar.model.Aluno;
import com.dougfsilva.controlesaidaescolar.repository.AlunoRepository;
import com.dougfsilva.controlesaidaescolar.repository.SaidaAlunoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeletaAlunoService {

	private final AlunoRepository alunoRepository;
	private final SaidaAlunoRepository saidaAlunoRepository;
	private final SecurityUtils securityUtils;

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@CacheEvict(value = "listaDeAlunos", allEntries = true)
	public void deletar(Long id) {
		Aluno aluno = alunoRepository.findByIdOrElseThrow(id);
		validarAlunoSemSaidasRegistradas(aluno);
		alunoRepository.delete(aluno);
		log.info("Usuário [{}] excluiu o aluno {} com sucesso.", 
	             securityUtils.getUsernameUsuarioAtual(), 
	             id);	
	}

	private void validarAlunoSemSaidasRegistradas(Aluno aluno) {
		if (saidaAlunoRepository.existsByAluno(aluno)) {
			throw new EntidadeEmUsoException(String
					.format("O aluno '%s' não pode ser removido pois há saídas registradas para ele", aluno.getNome()));
		}
	}
	
}
