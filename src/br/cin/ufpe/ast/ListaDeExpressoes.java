package br.cin.ufpe.ast;

import java.util.ArrayList;
import java.util.List;

import br.cin.ufpe.runtime.Escopo;

public class ListaDeExpressoes extends Expressao {

	private List<Expressao> expressoes;

	public ListaDeExpressoes(List<Expressao> expressoes) {
		this.expressoes = expressoes;
	}

	@Override
	public Object valor(Escopo escopo) {
		ArrayList<Object> val = new ArrayList<Object>();
		for (Expressao exp : expressoes)
			val.add(exp.valor(escopo));
		return val;
	}

	@Override
	public String valorTexto(Escopo escopo) {
		// TODO Auto-generated method stub
		return null;
	}
}
