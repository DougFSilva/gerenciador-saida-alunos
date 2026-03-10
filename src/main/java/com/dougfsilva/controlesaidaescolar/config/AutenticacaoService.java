package com.dougfsilva.controlesaidaescolar.config;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dougfsilva.controlesaidaescolar.exceptions.ErroDeAutenticacaoDeUsuarioException;
import com.dougfsilva.controlesaidaescolar.model.Usuario;
import com.dougfsilva.controlesaidaescolar.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticacaoService implements UserDetailsService{
	
	private final UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
	return usuario.orElseThrow(() -> new ErroDeAutenticacaoDeUsuarioException(
			"Usuario com email % " + username + " não encontrado"));
	}

}
