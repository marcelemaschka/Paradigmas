package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Escopo;
import br.cin.ufpe.runtime.OperadorBinario;

public class ExpressaoBinaria extends Expressao {

	private Expressao esquerda;
	private Expressao direita;
	private String operador;

	public ExpressaoBinaria(Expressao esquerda, Expressao direita,
			String operador) {
		this.esquerda = esquerda;
		this.direita = direita;
		this.operador = operador;
	}

	@Override
	public Object valor(Escopo escopo) {
		Object esq = esquerda.valor(escopo);
		Object dir = direita.valor(escopo);
		return OperadorBinario.operacaoBinaria(esq, dir, operador);
	}	
}
