package com.dougfsilva.controlesaidaescolar.service.usuario;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.dougfsilva.controlesaidaescolar.config.TokenService;
import com.dougfsilva.controlesaidaescolar.dto.AuthResponse;
import com.dougfsilva.controlesaidaescolar.dto.LoginForm;
import com.dougfsilva.controlesaidaescolar.exceptions.ErroDeAutenticacaoDeUsuarioException;
import com.dougfsilva.controlesaidaescolar.model.Usuario;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutenticacaoService {

	private static final String BEARER = "Bearer";

	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;

	public AuthResponse autenticar(LoginForm form) {
		UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(form.email(), form.senha());
		Authentication authentication = authenticationManager.authenticate(login);
		Usuario usuario = (Usuario) authentication.getPrincipal();
		validarUsuario(usuario);
		log.info("Usuário autenticado com sucesso: {}", usuario.getEmail());
		String token = tokenService.gerarToken(usuario);
		return new AuthResponse(token, BEARER);

	}

	private void validarUsuario(Usuario usuario) {
		if (!usuario.getSenhaAlterada()) {
			throw new ErroDeAutenticacaoDeUsuarioException(
					"Usuário precisa alterar a senha antes de acessar o sistema");
		}
	}
}
