package com.dougfsilva.controlesaidaescolar.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record AlunoUpdateForm(
		
		@NotNull(message = "O ID do aluno é obrigatório") 
		Long id,
		
		@NotBlank(message = "O nome é obrigatório") 
		@Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres") 
		String nome,
		
		@NotBlank(message = "A matrícula é obrigatória") 
		String matricula,

		@NotNull(message = "A data de nascimento é obrigatória") 
		@Past(message = "A data de nascimento deve ser uma data no passado") 
		LocalDate dataNascimento,

		@NotNull(message = "O ID da turma é obrigatório") 
		Long turmaId
		
		) {

}
