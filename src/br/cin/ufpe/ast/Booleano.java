package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Escopo;

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
	public Object valor(Escopo escopo) {
		return this.valor;
	}

	@Override
	public String toString() {
		return this.valor?"verdadeiro":"falso";
	}

	@Override
	public String valorTexto(Escopo escopo) {
		return toString();
	}

	
	
	

	
}
