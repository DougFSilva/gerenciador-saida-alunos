package com.dougfsilva.controlesaidaescolar.service.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dougfsilva.controlesaidaescolar.model.PerfilUsuario;
import com.dougfsilva.controlesaidaescolar.model.Usuario;
import com.dougfsilva.controlesaidaescolar.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BuscaUsuarioService {

	private final UsuarioRepository repository;
	
	@PreAuthorize("hasAnyRole('ADMIN', 'MASTER') or (hasRole('FUNCIONARIO') and #id == principal.id)")	
	public Usuario buscarPeloId(Long id) {
		return repository.findByIdOrElseThrow(id);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
	public Page<Usuario> buscarPeloNome(String nome, Pageable paginacao) {
		return repository.findByNomeContainingIgnoreCase(nome, paginacao);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
	public Page<Usuario> buscarPeloPerfil(PerfilUsuario perfil, Pageable paginacao) {
		return repository.findByPerfil(perfil, paginacao);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
	public Page<Usuario> buscarTodos(Pageable paginacao) {
		return repository.findAll(paginacao);
	}
}
