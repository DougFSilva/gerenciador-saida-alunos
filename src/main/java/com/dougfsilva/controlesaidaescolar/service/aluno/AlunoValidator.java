package com.dougfsilva.controlesaidaescolar.service.aluno;

import org.springframework.stereotype.Component;

import com.dougfsilva.controlesaidaescolar.exceptions.RegistroDuplicadoException;
import com.dougfsilva.controlesaidaescolar.repository.AlunoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlunoValidator {

	private final AlunoRepository repository;

    public void validarUnicidadeMatricula(String matricula) {
        if (repository.existsByMatricula(matricula)) {
            throw new RegistroDuplicadoException(
                String.format("Aluno com matrícula %s já existente na base de dados!", matricula)
            );
        }
    }
}
