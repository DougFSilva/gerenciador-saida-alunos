package com.dougfsilva.controlesaidaescolar.exceptions;

public class ErroDeAutenticacaoDeUsuarioException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ErroDeAutenticacaoDeUsuarioException(String mensagem) {
		super(mensagem);
	}

}
