package com.dougfsilva.controlesaidaescolar.service.usuario;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.dto.UsuarioUpdateForm;
import com.dougfsilva.controlesaidaescolar.model.Usuario;
import com.dougfsilva.controlesaidaescolar.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EditaUsuarioService {

	private final UsuarioRepository repository;
	private final UsuarioValidator usuarioValidator;
	private final SecurityUtils securityUtils;

	@PreAuthorize("hasAnyRole('ADMIN', 'MASTER') or (hasRole('FUNCIONARIO') and #form.id() == principal.id)")
	@Transactional
	public Usuario editar(Long id, UsuarioUpdateForm form) {
		Usuario usuario = repository.findByIdOrElseThrow(id);
		if (!usuario.getEmail().equalsIgnoreCase(form.email())) {
			usuarioValidator.validarUnicidadeEmail(form.email());
		}
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

}
