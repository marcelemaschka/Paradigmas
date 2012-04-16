package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Escopo;

public class Texto extends Expressao {

	private String valor;

	public Texto(String text) {
		valor = text;
	}

	@Override
	public Object valor(Escopo escopo) {
		return valor;
	}

	@Override
	public String valorTexto(Escopo escopo) {
		return valor;
	}
}
