package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Escopo;

public class Lista extends Expressao {

	private Expressao expressao;

	public Lista(Expressao expressao) {
		this.expressao = expressao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object valor(Escopo escopo) {
		Iterable<Object> exprIteravel = (Iterable<Object>) expressao
				.valor(escopo);
		return new br.cin.ufpe.runtime.Lista(exprIteravel);
	}
}
