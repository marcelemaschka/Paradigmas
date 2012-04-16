package br.cin.ufpe.ast;

public class Lista extends Expressao {

	private Range range;
	private ListaDeExpressoes expressoes;

	public Lista(Range range) {
		this.range = range;
	}

	public Lista(ListaDeExpressoes expressoes) {
		this.expressoes = expressoes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object valor(Escopo escopo) {
		Iterable<Object> exprIteravel = null;
		if (expressoes != null)
			exprIteravel = (Iterable<Object>) expressoes.valor(escopo);
		else if (range != null)
			exprIteravel = (Iterable<Object>) range.valor(escopo);
		return new br.cin.ufpe.runtime.Lista(exprIteravel);
	}

	@Override
	public String valorTexto(Escopo escopo) {
		// TODO Auto-generated method stub
		return null;
	}

}
