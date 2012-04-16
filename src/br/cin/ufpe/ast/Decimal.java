package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Escopo;

public class Decimal extends Expressao {

	private double valor;

	public Decimal(String text) {
		valor = Double.parseDouble(text);
	}

	@Override
	public Object valor(Escopo escopo) {
		return valor;
	}

	@Override
	public String valorTexto(Escopo escopo) {
		return Double.toString(valor);
	}
}
