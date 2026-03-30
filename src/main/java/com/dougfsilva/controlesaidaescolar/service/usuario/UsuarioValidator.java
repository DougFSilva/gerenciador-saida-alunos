package com.dougfsilva.controlesaidaescolar.service.usuario;

import org.springframework.stereotype.Component;

import com.dougfsilva.controlesaidaescolar.exceptions.RegistroDuplicadoException;
import com.dougfsilva.controlesaidaescolar.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {


	private final UsuarioRepository repository;

	public void validarUnicidadeEmail(String email) {
		if (repository.existsByEmail(email)) {
			throw new RegistroDuplicadoException(
					String.format("Usuário com email '%s' já existente no banco de dados", email));
		}
	}
	
	public void validarUnicidadeCpf(String cpf) {
		if (repository.existsByCpf(cpf)) {
			throw new RegistroDuplicadoException(
					String.format("Usuário com cpf '%s' já existente no banco de dados", cpf));
		}
	}
	
}
