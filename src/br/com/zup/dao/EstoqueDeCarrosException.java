package br.com.zup.dao;

public class EstoqueDeCarrosException extends Exception {

	private static final long serialVersionUID = -7963053152689950310L;

	private String mensagem;

	public EstoqueDeCarrosException(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return this.mensagem;
	}

}
