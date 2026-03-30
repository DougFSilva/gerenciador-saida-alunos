package com.dougfsilva.controlesaidaescolar.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginForm(

		@NotBlank(message = "O email deve ser informado.") 
		String email,

		@NotBlank(message = "A senha deve ser informado.")
		String senha) {

}
