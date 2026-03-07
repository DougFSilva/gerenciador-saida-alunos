package com.dougfsilva.controlesaidaescolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dougfsilva.controlesaidaescolar.exceptions.ObjetoNaoEncontradoException;
import com.dougfsilva.controlesaidaescolar.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	default Usuario findByIdOrElseThrow(Long ID) {
		return findById(ID).orElseThrow(
				() -> new ObjetoNaoEncontradoException(String.format("Usuário com id %d não encontrado!", ID)));
	}
}
