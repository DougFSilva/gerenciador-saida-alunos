package com.dougfsilva.controlesaidaescolar.service.usuario;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dougfsilva.controlesaidaescolar.config.PasswordService;
import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.dto.UsuarioForm;
import com.dougfsilva.controlesaidaescolar.model.Usuario;
import com.dougfsilva.controlesaidaescolar.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CriaUsuarioService {
	
	private final UsuarioRepository repository;
	private final PasswordService passwordService;
	private final UsuarioValidator usuarioValidator;
	private final SecurityUtils securityUtils;
	
	@Value("${senha.padrao}")
    private String senha;
	
	@Transactional
	@PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
	public Usuario criar(UsuarioForm form) {
		usuarioValidator.validarUnicidadeEmail(form.email());
		String senhaCriptografada = passwordService.criptografar(senha);
		Usuario usuario = new Usuario(form.nome(), form.cpf(), form.email(), senhaCriptografada, form.perfil());
		Usuario usuarioCriado = repository.save(usuario);
		log.info("Usuário [{}] criou o Usuário {} com sucesso.", 
	             securityUtils.getUsuarioAtual(), 
	             usuarioCriado.getId());
		return usuarioCriado;
	}

}
