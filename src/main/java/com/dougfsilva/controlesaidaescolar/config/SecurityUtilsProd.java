package com.dougfsilva.controlesaidaescolar.config;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dougfsilva.controlesaidaescolar.exceptions.ErroDeAutenticacaoDeUsuarioException;
import com.dougfsilva.controlesaidaescolar.model.PerfilUsuario;
import com.dougfsilva.controlesaidaescolar.model.Usuario;

@Component
@Profile("!dev")
public class SecurityUtilsProd implements SecurityUtils{

	@Override
	public String getUsernameUsuarioAtual() {
		return getUsuarioAtual().getUsername();
	}

	@Override
	public Usuario getUsuarioAtual() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof Usuario usuario) {
			return usuario;
		}

		throw new ErroDeAutenticacaoDeUsuarioException("Usuário não autenticado ou sessão expirada.");

	}

	@Override
	public boolean isFuncionario() {
		Usuario usuarioAtual = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usuarioAtual.getPerfil().equals(PerfilUsuario.FUNCIONARIO);
	}

	@Override
	public boolean isAdminOuMaster() {
		Usuario usuarioAtual = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PerfilUsuario perfil = usuarioAtual.getPerfil();
		return perfil.equals(PerfilUsuario.ADMIN) || perfil.equals(PerfilUsuario.MASTER);
	}

}
