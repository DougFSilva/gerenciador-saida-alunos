package com.dougfsilva.controlesaidaescolar.service.usuario;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dougfsilva.controlesaidaescolar.config.SecurityUtils;
import com.dougfsilva.controlesaidaescolar.exceptions.EntidadeEmUsoException;
import com.dougfsilva.controlesaidaescolar.model.Usuario;
import com.dougfsilva.controlesaidaescolar.repository.SaidaAlunoRepository;
import com.dougfsilva.controlesaidaescolar.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeletaUsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final SaidaAlunoRepository saidaAlunoRepository;
	private final SecurityUtils securityUtils;

	@Transactional
	@PreAuthorize("hasAnyRole('ADMIN', 'MASTER')")
	@CacheEvict(value = "listaDeUsuarios", allEntries = true)
	public void deletar(Long id) {
		Usuario usuario = usuarioRepository.findByIdOrElseThrow(id);
		validarUsuarioSemRegistroDeSaida(usuario);
		usuarioRepository.delete(usuario);
		log.info("Usuário [{}] deletou o Usuário {} com sucesso.", 
	             securityUtils.getUsernameUsuarioAtual(), 
	             id);
	}

	private void validarUsuarioSemRegistroDeSaida(Usuario usuario) {
		if (saidaAlunoRepository.existsBySolicitacaoSolicitadaPor(usuario)) {
			throw new EntidadeEmUsoException(
					String.format("O usuário '%s' não pode ser removido pois existem registro de saída de alunos associados a ele."));
		}
	}

}
