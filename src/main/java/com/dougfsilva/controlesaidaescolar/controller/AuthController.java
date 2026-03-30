package com.dougfsilva.controlesaidaescolar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dougfsilva.controlesaidaescolar.dto.AlteraSenhaForm;
import com.dougfsilva.controlesaidaescolar.dto.AuthResponse;
import com.dougfsilva.controlesaidaescolar.dto.LoginForm;
import com.dougfsilva.controlesaidaescolar.service.usuario.AlteraSenhaService;
import com.dougfsilva.controlesaidaescolar.service.usuario.AutenticacaoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AutenticacaoService autenticacaoService;
	private final AlteraSenhaService alteraSenhaService;

	@PostMapping
	@Operation(
		summary = "Autenticar usuário", 
		description = "Autentica o usuário e retorna um token JWT para acesso às demais funcionalidades da API."
			+ "Este token possui as claims perfil, nome e senha alterada, além do email e data de expiração"
	)
	public ResponseEntity<AuthResponse> autenticar(@Valid @RequestBody LoginForm form) {
		AuthResponse authResponse = autenticacaoService.autenticar(form);
		return ResponseEntity.ok().body(authResponse);
	}

	@PatchMapping("/alterar-senha")
	@Operation(summary = "Alterar senha", description = "Altera a senha de um usuário existente")
	public ResponseEntity<Void> alterarSenha(@Valid @RequestBody AlteraSenhaForm form) {
		alteraSenhaService.alterar(form);
		return ResponseEntity.ok().build();
	}

}
