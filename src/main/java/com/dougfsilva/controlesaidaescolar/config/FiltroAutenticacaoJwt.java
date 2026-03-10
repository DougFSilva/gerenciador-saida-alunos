package com.dougfsilva.controlesaidaescolar.config;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dougfsilva.controlesaidaescolar.dto.ErroResponse;
import com.dougfsilva.controlesaidaescolar.exceptions.ErroComTokenJwtException;
import com.dougfsilva.controlesaidaescolar.exceptions.ErroDeAutenticacaoDeUsuarioException;
import com.dougfsilva.controlesaidaescolar.model.Usuario;
import com.dougfsilva.controlesaidaescolar.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FiltroAutenticacaoJwt extends OncePerRequestFilter {

	private final TokenService tokenService;
	private final ObjectMapper objectMapper;
	private final UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recuperarToken(request);
		if (token != null) {
			try {
				if (tokenService.validarToken(token)) {
					autenticar(token);
				}
			} catch (ErroComTokenJwtException e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json");
				ErroResponse erro = new ErroResponse(OffsetDateTime.now(ZoneOffset.UTC),
						HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), request.getRequestURI());
				response.getWriter().write(objectMapper.writeValueAsString(erro));
				return;
			}
		}

		filterChain.doFilter(request, response);

	}

	private void autenticar(String token) {
		String username = tokenService.extrairUsernameDoToken(token);
		Usuario usuario = usuarioRepository.findByEmail(username)
				.orElseThrow(() -> new ErroDeAutenticacaoDeUsuarioException(
						String.format("Usuário com email %s não encontrado", username)));
		UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(usuario, null,
				usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(autenticacao);
	}

	private String recuperarToken(HttpServletRequest request) {
		String headerToken = request.getHeader("Authorization");
		if (headerToken != null && headerToken.startsWith("Bearer ")) {
			return headerToken.substring(7);
		}

		String paramToken = request.getParameter("token");
		return (paramToken != null && !paramToken.isBlank()) ? paramToken : null;
	}

}
