package br.cin.ufpe.ast;

public class Texto extends Expressao {

	private String valor;

	public Texto(String text) {
		valor = text;
	}

	@Override
	public Object valor() {
		return valor;
	}
}
