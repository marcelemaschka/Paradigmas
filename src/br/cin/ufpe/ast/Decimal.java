package br.cin.ufpe.ast;

public class Decimal extends Expressao {

	private double valor;

	public Decimal(String text) {
		valor = Double.parseDouble(text);
	}

	@Override
	public Object valor() {
		return valor;
	}
}
