package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Escopo;
import br.cin.ufpe.runtime.IteradorDeIntervalo;

public class Intervalo extends Expressao {

	private Expressao inicio;
	private Expressao fim;
	private Expressao passo;

	public Intervalo(Expressao inicio, Expressao fim, Expressao passo) {
		this.inicio = inicio;
		this.fim = fim;
		this.passo = passo;
	}

	@Override
	public Object valor(Escopo escopo) {
		Object inicio = this.inicio.valor(escopo);
		if (!(inicio instanceof Long))
			throw new IllegalArgumentException(
					"Início do intervalo deve ter valor inteiro.");
		Object fim = this.fim.valor(escopo);
		if (!(fim instanceof Long))
			throw new IllegalArgumentException(
					"Fim do intervalo deve ter valor inteiro.");
		Long passo = null;
		if (this.passo != null) {
			Object val = this.passo.valor(escopo);
			if (!(val instanceof Long))
				throw new IllegalArgumentException(
						"Passo do intervalo deve ter valor inteiro.");
			passo = (Long) val;
		}
		return new IteradorDeIntervalo((Long) inicio, (Long) fim, passo);
	}
}
