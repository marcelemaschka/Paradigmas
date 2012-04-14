package br.cin.ufpe.ast;

public class Booleano extends Expressao {

	private boolean valor;

	public Booleano(String text) {
		if (text.equals("verdadeiro"))
			valor = true;
		else if (text.equals("falso"))
			valor = false;
		else
			throw new IllegalArgumentException();
	}

	@Override
	public Object valor() {
		return valor;
	}
}
