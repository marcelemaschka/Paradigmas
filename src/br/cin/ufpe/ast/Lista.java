package br.cin.ufpe.ast;

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

	@Override
	public String valorTexto(Escopo escopo) {
		// TODO Auto-generated method stub
		return null;
	}

}
