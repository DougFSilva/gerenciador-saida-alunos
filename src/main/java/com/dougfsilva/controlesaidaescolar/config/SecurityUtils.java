package com.dougfsilva.controlesaidaescolar.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.dougfsilva.controlesaidaescolar.exceptions.ErroDeAutenticacaoDeUsuarioException;
import com.dougfsilva.controlesaidaescolar.model.PerfilUsuario;
import com.dougfsilva.controlesaidaescolar.model.Usuario;

@Component
public class SecurityUtils {

	public String getUsernameUsuarioAtual() {
		return getUsuarioAtual().getUsername();
	}

	public Usuario getUsuarioAtual() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof Usuario usuario) {
			return usuario;
		}

		throw new ErroDeAutenticacaoDeUsuarioException("Usuário não autenticado ou sessão expirada.");

	}

	public boolean isFuncionario() {
		Usuario usuarioAtual = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usuarioAtual.getPerfil().equals(PerfilUsuario.FUNCIONARIO);
	}

	public boolean isAdminOuMaster() {
		Usuario usuarioAtual = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PerfilUsuario perfil = usuarioAtual.getPerfil();
		return perfil.equals(PerfilUsuario.ADMIN) || perfil.equals(PerfilUsuario.MASTER);
	}
}
