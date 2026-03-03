package com.dougfsilva.controlesaidaescolar.service.turma;

import com.dougfsilva.controlesaidaescolar.dto.TurmaForm;
import com.dougfsilva.controlesaidaescolar.exceptions.RegistroDuplicadoException;
import com.dougfsilva.controlesaidaescolar.model.Turma;
import com.dougfsilva.controlesaidaescolar.repository.TurmaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CriaTurmaService {

	private final TurmaRepository repository;

	public Turma criar(TurmaForm form) {
		validarUnicaTurma(form.nome(), form.anoLetivo());
		Turma turma = new Turma(null, form.nome(), form.turno(), form.anoLetivo());
		Turma turmaCriada = repository.save(turma);
		log.info("Turma criada com sucesso! ID: {}", turmaCriada.getId());
		return turmaCriada;
	}

	private void validarUnicaTurma(String nome, String anoLetivo) {
		if (repository.existsByNomeAndAnoLetivo(nome, anoLetivo)) {
			throw new RegistroDuplicadoException("Essa turma já existe na base de dados!");
		}
	}
}
