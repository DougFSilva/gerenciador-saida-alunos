package com.dougfsilva.controlesaidaescolar.exceptions;

public class ErroComTokenJwtException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ErroComTokenJwtException(String mensagem) {
		super(mensagem);
	}

}
