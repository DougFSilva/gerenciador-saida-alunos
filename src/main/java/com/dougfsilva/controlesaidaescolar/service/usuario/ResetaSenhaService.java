package com.dougfsilva.controlesaidaescolar.service.usuario;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dougfsilva.controlesaidaescolar.config.PasswordService;
import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.model.Usuario;
import com.dougfsilva.controlesaidaescolar.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResetaSenhaService {

	private final UsuarioRepository repository;
	private final PasswordService passwordService;
	private final SecurityUtils securityUtils;
	
	@Value("${senha.padrao}")
    private String senha;
	
	@Transactional
	@PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
	public void resetar(Long id) {
		Usuario usuario = repository.findByIdOrElseThrow(id);
		String senhaCriptografada = passwordService.criptografar(senha);
		usuario.setSenha(senhaCriptografada);
		usuario.setSenhaAlterada(false);
		Usuario usuarioComSenhaResetada = repository.save(usuario);
		log.info("Usuário [{}] resetou a senha do Usuário {} com sucesso.", 
	             securityUtils.getUsernameUsuarioAtual(), 
	             usuarioComSenhaResetada.getId());
	}
}
