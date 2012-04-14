package br.cin.ufpe.ast;

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
	public Object valor() {
		Number esq = (Number) esquerda.valor();
		Number dir = (Number) direita.valor();
		if (operador.equals("+"))
			return esq.doubleValue() + dir.doubleValue();
		else if (operador.equals("-"))
			return esq.doubleValue() - dir.doubleValue();
		else if (operador.equals("*"))
			return esq.doubleValue() * dir.doubleValue();
		else if (operador.equals("/"))
			return esq.doubleValue() / dir.doubleValue();
		throw new UnsupportedOperationException();
	}

}
