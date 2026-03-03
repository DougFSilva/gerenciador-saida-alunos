package com.dougfsilva.controlesaidaescolar.service.turma;

import com.dougfsilva.controlesaidaescolar.model.Turma;
import com.dougfsilva.controlesaidaescolar.repository.AlunoRepository;
import com.dougfsilva.controlesaidaescolar.repository.TurmaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DeletaTurmaService {

	private final TurmaRepository turmaRepository;
	private final AlunoRepository alunoRepository;

	@Transactional
	public void deletar(Long id) {
		Turma turma = turmaRepository.findByIdOrElseThrow(id);
		validarTurmaSemAlunos(turma);
		turmaRepository.delete(turma);
		log.info("Turma {} excluída com sucesso.", id);
	}

	private void validarTurmaSemAlunos(Turma turma) {
		if (alunoRepository.existsByTurma(turma)) {
			String.format("A turma '%s' não pode ser removida pois existem alunos matriculados nela.", turma.getNome());
		}
	}
}
