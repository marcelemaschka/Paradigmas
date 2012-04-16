package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Escopo;
import br.cin.ufpe.runtime.IteradorDeIntervalo;

public class Intervalo extends Expressao {

	private Inteiro inicio;
	private Inteiro fim;
	private Inteiro passo;

	public Intervalo(Inteiro inicio, Inteiro fim, Inteiro passo) {
		this.inicio = inicio;
		this.fim = fim;
		this.passo = passo;
	}

	@Override
	public Object valor(Escopo escopo) {
		Long inicio = (Long) this.inicio.valor(escopo);
		Long fim = (Long) this.fim.valor(escopo);
		Long passo = null;
		if (this.passo != null)
			passo = (Long) this.passo.valor(escopo);
		return new IteradorDeIntervalo(inicio, fim, passo);
	}

	@Override
	public String valorTexto(Escopo escopo) {
		// TODO Auto-generated method stub
		return null;
	}

}
