package com.dougfsilva.controlesaidaescolar.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dougfsilva.controlesaidaescolar.exceptions.ErroComTokenJwtException;
import com.dougfsilva.controlesaidaescolar.exceptions.ErroInesperadoException;
import com.dougfsilva.controlesaidaescolar.model.Usuario;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long tempoExpiracao;
	
	public String gerarToken(Usuario usuario) {
		SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
		Map<String, Object> claims = new HashMap<>();
		claims.put("perfil", usuario.getPerfil().name());
		claims.put("nome", usuario.getNome());
		claims.put("senhaAlterada", usuario.getSenhaAlterada());
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(usuario.getEmail())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + tempoExpiracao * 1000 * 60 * 60))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	public String extrairUsernameDoToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	public boolean validarToken(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			throw new ErroComTokenJwtException("O token JWT expirou. Faça login novamente!");
		} catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
			throw new ErroComTokenJwtException("Token JWT inválido. Faça login novamente!");
		} catch (Exception e) {
			throw new ErroInesperadoException("Erro inesperado na validação de Token!");
		}
	}
}
