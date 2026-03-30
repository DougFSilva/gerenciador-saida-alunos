package com.dougfsilva.controlesaidaescolar.service.usuario;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.dto.UsuarioUpdateForm;
import com.dougfsilva.controlesaidaescolar.exceptions.RegraDeNegocioException;
import com.dougfsilva.controlesaidaescolar.model.Usuario;
import com.dougfsilva.controlesaidaescolar.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EditaUsuarioService {
	
	@Value("${email.master}")
	private String emailMaster;

	private final UsuarioRepository repository;
	private final UsuarioValidator usuarioValidator;
	private final SecurityUtils securityUtils;

	@PreAuthorize("hasAnyRole('ADMIN', 'MASTER') or (hasRole('FUNCIONARIO') and #form.id() == principal.id)")
	@Transactional
	@CacheEvict(value = "listaDeUsuarios", allEntries = true)
	public Usuario editar(Long id, UsuarioUpdateForm form) {
		Usuario usuario = repository.findByIdOrElseThrow(id);
		validarNaoEUsuarioMaster(usuario);
		validarUnicidadeDeAtributos(usuario, form);
		usuario.setCpf(form.cpf());
		usuario.setEmail(form.email());
		usuario.setNome(form.nome());
		if (securityUtils.isAdminOuMaster()) {
			usuario.setPerfil(form.perfil());
			usuario.setAtivo(form.ativo());
		} else {
			log.warn("Usuário {} tentou alterar campos restritos, mas não possui permissão.", securityUtils.getUsernameUsuarioAtual());
		}
		Usuario usuarioEditado = repository.save(usuario);
		log.info("Usuário [{}] editou o Usuário {} com sucesso.", securityUtils.getUsernameUsuarioAtual(),
				usuarioEditado.getId());
		return usuarioEditado;
	}
	
	private void validarNaoEUsuarioMaster(Usuario usuario) {
		if (usuario.getEmail().equalsIgnoreCase(emailMaster)) {
			throw new RegraDeNegocioException("O usuário MASTER não pode ser editado!");
		}
	}
	
	private void validarUnicidadeDeAtributos(Usuario usuario, UsuarioUpdateForm form) {
		if (!usuario.getEmail().equalsIgnoreCase(form.email())) {
			usuarioValidator.validarUnicidadeEmail(form.email());
		}
		if (!usuario.getCpf().equalsIgnoreCase(form.cpf())) {
			usuarioValidator.validarUnicidadeCpf(form.cpf());
		}
	}

}
