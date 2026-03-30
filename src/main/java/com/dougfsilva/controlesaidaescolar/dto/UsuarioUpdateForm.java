package com.dougfsilva.controlesaidaescolar.dto;

import org.hibernate.validator.constraints.br.CPF;

import com.dougfsilva.controlesaidaescolar.model.PerfilUsuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateForm(
		
		@NotBlank(message = "O nome é obrigatório.") 
		@Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.") 
		String nome,

		@NotBlank(message = "O CPF é obrigatório.")
		@CPF(message = "CPF em formato inválido.") 
		String cpf,

		@NotBlank(message = "O e-mail é obrigatório.") 
		@Email(message = "E-mail em formato inválido.")
	    @Size(min = 3, max = 100, message = "O email deve ter entre 3 e 100 caracteres.")
		String email,

		@NotNull(message = "O perfil do usuário é obrigatório.") 
		PerfilUsuario perfil,
		
		@NotNull(message = "O campo ativo é obrigatório.") 
		Boolean ativo

) {

}
