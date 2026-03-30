package com.dougfsilva.controlesaidaescolar.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.dougfsilva.controlesaidaescolar.model.PerfilUsuario;
import com.dougfsilva.controlesaidaescolar.model.Usuario;

@Component
@Profile("dev")
public class SecurityUtilsDev implements SecurityUtils {

	private Usuario usuarioMock;

	public SecurityUtilsDev() {
		usuarioMock = new Usuario(1L, "dev", "111.222.333.44", "dev@email.com", "123456", PerfilUsuario.ADMIN, true, true);
	}

	@Override
	public String getUsernameUsuarioAtual() {
		return usuarioMock.getUsername();
	}

	@Override
	public Usuario getUsuarioAtual() {
		return usuarioMock;
	}

	@Override
	public boolean isFuncionario() {
		return usuarioMock.getPerfil().equals(PerfilUsuario.FUNCIONARIO);
	}

	@Override
	public boolean isAdminOuMaster() {
		PerfilUsuario perfil = usuarioMock.getPerfil();
		return perfil.equals(PerfilUsuario.ADMIN) || perfil.equals(PerfilUsuario.MASTER);
	}

}
