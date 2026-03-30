package com.dougfsilva.controlesaidaescolar.controller;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import javax.security.auth.login.AccountExpiredException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.dougfsilva.controlesaidaescolar.dto.ErroResponse;
import com.dougfsilva.controlesaidaescolar.exceptions.EntidadeEmUsoException;
import com.dougfsilva.controlesaidaescolar.exceptions.ErroComTokenJwtException;
import com.dougfsilva.controlesaidaescolar.exceptions.ErroDeAutenticacaoDeUsuarioException;
import com.dougfsilva.controlesaidaescolar.exceptions.ErroInesperadoException;
import com.dougfsilva.controlesaidaescolar.exceptions.ObjetoNaoEncontradoException;
import com.dougfsilva.controlesaidaescolar.exceptions.ProcessamentoFotoException;
import com.dougfsilva.controlesaidaescolar.exceptions.RegistroDuplicadoException;
import com.dougfsilva.controlesaidaescolar.exceptions.RegraDeNegocioException;
import com.dougfsilva.controlesaidaescolar.exceptions.SenhaInvalidaException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<ErroResponse> entidadeEmUsoException(EntidadeEmUsoException e, 
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(ErroDeAutenticacaoDeUsuarioException.class)
	public ResponseEntity<ErroResponse> erroDeAutenticacaoDeUsuarioException(
			ErroDeAutenticacaoDeUsuarioException e,
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.UNAUTHORIZED.value(), 
				e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
	}

	@ExceptionHandler(ErroComTokenJwtException.class)
	public ResponseEntity<ErroResponse> erroComTokenJwtException(ErroComTokenJwtException e, 
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.UNAUTHORIZED.value(),
				e.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
	}

	@ExceptionHandler(ProcessamentoFotoException.class)
	public ResponseEntity<ErroResponse> processamentoFotoException(
			ProcessamentoFotoException e,
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(RegistroDuplicadoException.class)
	public ResponseEntity<ErroResponse> registroDuplicadoException(
			RegistroDuplicadoException e,
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	
	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<ErroResponse> objetoNaoEncontradoException(
			ObjetoNaoEncontradoException e,
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.NOT_FOUND.value(), 
				e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(SenhaInvalidaException.class)
	public ResponseEntity<ErroResponse> senhaInvalidaException(
			SenhaInvalidaException e,
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(RegraDeNegocioException.class)
	public ResponseEntity<ErroResponse> regraDeNegocioException(
			RegraDeNegocioException e,
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroResponse> methodArgumentNotValidException(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		 StringBuilder erros = new StringBuilder();

		    e.getBindingResult().getAllErrors().forEach(error -> {
		        String msg = error.getDefaultMessage();
		        erros.append(msg).append(" ");
		    });
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.BAD_REQUEST.value(), 
				erros.toString(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErroResponse> illegalArgumentException(IllegalArgumentException e,
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErroResponse> methodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException e,
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.BAD_REQUEST.value(), 
				e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErroResponse> badCredentialsException(BadCredentialsException e,
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.UNAUTHORIZED.value(), 
				e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
	}
	
	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ErroResponse> authorizationDeniedException(AuthorizationDeniedException e,
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.FORBIDDEN.value(), 
				"Acesso negado, você não tem permissão para realizar essa operação",
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
	}
	
	@ExceptionHandler(AccountExpiredException.class)
	public ResponseEntity<ErroResponse> accountExpiredException(AccountExpiredException e,
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.FORBIDDEN.value(), 
				"Usuário com conta expirada. Contate um administrador e renove a data de validade para voltar a ter acesso",
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErroResponse> authenticationException(AuthenticationException e,
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.UNAUTHORIZED .value(), 
				"Falha na autenticação. Verifique usuário e senha.",
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED ).body(erro);
	}
	
	
	@ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
	public ResponseEntity<ErroResponse> dataIntegrityViolationException(
	        org.springframework.dao.DataIntegrityViolationException e,
	        HttpServletRequest request) {
	    
	    ErroResponse erro = new ErroResponse(
	            OffsetDateTime.now(ZoneOffset.UTC), 
	            HttpStatus.CONFLICT.value(), 
	            "Violação de integridade de dados: o recurso está sendo usado por outro registro ou viola uma regra de banco de dados.",
	            request.getRequestURI());
	            
	    return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
	
	@ExceptionHandler(ErroInesperadoException.class)
	public ResponseEntity<ErroResponse> erroInesperadoException(ErroInesperadoException e,
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				"Erro inesperado: " + e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErroResponse> exception(Exception e,
			HttpServletRequest request) {
		ErroResponse erro = new ErroResponse(
				OffsetDateTime.now(ZoneOffset.UTC), 
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				"Erro inesperado: " + e.getMessage(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
	}
	
}
