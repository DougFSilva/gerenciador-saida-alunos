package com.dougfsilva.controlesaidaescolar.dto;

import com.dougfsilva.controlesaidaescolar.model.Turno;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TurmaForm(
		
		@NotBlank(message = "O nome da turma é obrigatório.")
	    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres.")
	    String nome,

	    @NotNull(message = "O turno deve ser informado.")
	    Turno turno,

	    @NotNull(message = "O ano letivo é obrigatório.")
		@Min(value = 1000, message = "O ano letivo deve ter 4 dígitos.")
		@Max(value = 9999, message = "O ano letivo deve ter 4 dígitos.")
	    Integer anoLetivo
	    
		) {

}
