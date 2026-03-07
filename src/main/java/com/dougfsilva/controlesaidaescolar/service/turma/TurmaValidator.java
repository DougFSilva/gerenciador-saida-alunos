package com.dougfsilva.controlesaidaescolar.service.turma;

import org.springframework.stereotype.Component;

import com.dougfsilva.controlesaidaescolar.exceptions.RegistroDuplicadoException;
import com.dougfsilva.controlesaidaescolar.repository.TurmaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TurmaValidator {

	private final TurmaRepository repository;

	public void validarUnicidade(String nome, Integer anoLetivo) {
		if (repository.existsByNomeAndAnoLetivo(nome, anoLetivo)) {
			throw new RegistroDuplicadoException(
					String.format("Turma de nome %s no ano %d já existente na base de dados!", nome, anoLetivo));
		}
	}
}
