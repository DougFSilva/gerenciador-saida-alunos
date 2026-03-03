package com.dougfsilva.controlesaidaescolar.exceptions;

public class ObjetoNaoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ObjetoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

}
