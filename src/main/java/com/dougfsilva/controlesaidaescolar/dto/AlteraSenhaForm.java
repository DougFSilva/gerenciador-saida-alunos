package com.dougfsilva.controlesaidaescolar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AlteraSenhaForm(
		
	    @NotBlank(message = "A senha atual é obrigatória.")
	    String senhaAtual,

	    @NotBlank
	    @Pattern(
	        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
	        message = "A senha deve conter pelo menos 8 caracteres, incluindo letras maiúsculas, minúsculas, números e caracteres especiais."
	    )
	    String novaSenha
	    
	    
	    ) {

	
}
