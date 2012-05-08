package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Escopo;
import br.cin.ufpe.runtime.Operacao;

public class ExpressaoUnaria extends Expressao {

	private Expressao operando;
	private String operador;

	public ExpressaoUnaria(String operador, Expressao operando) {
		this.operador = operador;
		this.operando = operando;
	}

	@Override
	public Object valor(Escopo escopo) {
		Object alvo = operando.valor(escopo);
		return Operacao.unaria(alvo, operador);
	}

}
