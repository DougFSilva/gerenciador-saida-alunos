package com.dougfsilva.controlesaidaescolar.exceptions;

public class ErroInesperadoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ErroInesperadoException(String mensagem) {
		super(mensagem);
	}

}
