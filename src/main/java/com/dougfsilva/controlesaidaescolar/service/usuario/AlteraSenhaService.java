package com.dougfsilva.controlesaidaescolar.service.usuario;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dougfsilva.controlesaidaescolar.config.PasswordService;
import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.dto.AlteraSenhaForm;
import com.dougfsilva.controlesaidaescolar.exceptions.ObjetoNaoEncontradoException;
import com.dougfsilva.controlesaidaescolar.exceptions.SenhaInvalidaException;
import com.dougfsilva.controlesaidaescolar.model.Usuario;
import com.dougfsilva.controlesaidaescolar.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlteraSenhaService {

	private final UsuarioRepository repository;
	private final PasswordService passwordService;
	private final SecurityUtils securityUtils;

	@Transactional
	@PreAuthorize("isAuthenticated()")
	@CacheEvict(value = "listaDeUsuarios", allEntries = true)
	public void alterar(AlteraSenhaForm form) {
		String emailUsuario = securityUtils.getUsernameUsuarioAtual();
		Usuario usuario = repository.findByEmail(emailUsuario).orElseThrow(() -> new ObjetoNaoEncontradoException(
				String.format("Usuário com email '%s' não encontrado.", emailUsuario)));

		if (!passwordService.validar(form.senhaAtual(), usuario.getSenha())) {
			throw new SenhaInvalidaException("A senha atual informada está incorreta.");
		}
		
		if (passwordService.validar(form.novaSenha(), usuario.getSenha())) {
            throw new SenhaInvalidaException("A nova senha não pode ser igual à senha atual.");
        }

		usuario.setSenha(passwordService.criptografar(form.novaSenha()));
		usuario.setSenhaAlterada(true);

		repository.save(usuario);
		log.info("Usuário [{}] alterou sua senha com sucesso.", securityUtils.getUsernameUsuarioAtual());
	}

}
