package com.dougfsilva.controlesaidaescolar.dto;

import com.dougfsilva.controlesaidaescolar.model.PerfilUsuario;
import com.dougfsilva.controlesaidaescolar.model.Usuario;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class UsuarioDto {

	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private PerfilUsuario perfil;
	private Boolean ativo = true;
	private Boolean senhaAlterada = false;
	
	private UsuarioDto(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.cpf = usuario.getCpf();
		this.email = usuario.getEmail();
		this.perfil = usuario.getPerfil();
		this.ativo = usuario.getAtivo();
		this.senhaAlterada = usuario.getSenhaAlterada();
	}
	
	public static UsuarioDto toDto(Usuario usuario) {
		return new UsuarioDto(usuario);
	}

}
