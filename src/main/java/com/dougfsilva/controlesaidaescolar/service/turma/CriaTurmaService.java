package com.dougfsilva.controlesaidaescolar.service.turma;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.dto.TurmaForm;
import com.dougfsilva.controlesaidaescolar.exceptions.RegistroDuplicadoException;
import com.dougfsilva.controlesaidaescolar.model.Turma;
import com.dougfsilva.controlesaidaescolar.repository.TurmaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CriaTurmaService {

	private final TurmaRepository repository;
	private final SecurityUtils securityUtils;

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public Turma criar(TurmaForm form) {
		validarUnicaTurma(form.nome(), form.anoLetivo());
		Turma turma = new Turma(null, form.nome(), form.turno(), form.anoLetivo(), true);
		Turma turmaCriada = repository.save(turma);
		log.info("Usuário [{}] criou a turma com sucesso! ID: {}", 
	             securityUtils.getUsuarioAtual(), 
	             turmaCriada.getId());
		return turmaCriada;
	}

	private void validarUnicaTurma(String nome, Integer anoLetivo) {
		if (repository.existsByNomeAndAnoLetivo(nome, anoLetivo)) {
			throw new RegistroDuplicadoException(
					String.format("Turma de nome %s do ano letivo %s já existente na base de dados!", nome, anoLetivo));
		}
	}
}
