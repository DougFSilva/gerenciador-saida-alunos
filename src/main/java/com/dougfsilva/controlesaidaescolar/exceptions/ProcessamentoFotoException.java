package com.dougfsilva.controlesaidaescolar.exceptions;

public class ProcessamentoFotoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ProcessamentoFotoException(String mensagem) {
		super(mensagem);
	}
	
	public ProcessamentoFotoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
