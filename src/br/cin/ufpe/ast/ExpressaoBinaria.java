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
		Object esq = esquerda.valor();
		Object dir = direita.valor();
		if (esq instanceof Number)
			return valorNumerico((Number) esq, dir);
		else if (esq instanceof String)
			return valorTexto((String) esq, dir);
		else if (esq instanceof Boolean && dir instanceof Boolean)
			return valorBooleano((Boolean) esq, (Boolean) dir);
		throw new IllegalArgumentException();
	}

	private Object valorBooleano(boolean esq, boolean dir) {
		if (operador.equals("&&"))
			return esq && dir;
		else if (operador.equals("||"))
			return esq || dir;
		throw new UnsupportedOperationException();
	}

	private Object valorTexto(String esq, Object direita) {
		// FIXME Tratar o caso em que a expressao esquerda é um número
		// e a direita é uma String
		if (operador.equals("+"))
			return esq + direita.toString();
		// TODO Implementar operador de igualdade '==' para Strings
		// Opcionalmente colocar suporte para '*' (multiplicação de String
		// por número)
		throw new UnsupportedOperationException();
	}

	private Object valorNumerico(Number esq, Object direita) {
		if (!(direita instanceof Number))
			throw new IllegalArgumentException();
		Number dir = (Number) direita;
		if (operador.equals("+"))
			return esq.doubleValue() + dir.doubleValue();
		else if (operador.equals("-"))
			return esq.doubleValue() - dir.doubleValue();
		else if (operador.equals("*"))
			return esq.doubleValue() * dir.doubleValue();
		else if (operador.equals("/"))
			return esq.doubleValue() / dir.doubleValue();
		else if (operador.equals(">="))
			return esq.doubleValue() >= dir.doubleValue();
		else if (operador.equals(">"))
			return esq.doubleValue() > dir.doubleValue();
		else if (operador.equals("<="))
			return esq.doubleValue() <= dir.doubleValue();
		else if (operador.equals("<"))
			return esq.doubleValue() < dir.doubleValue();
		else if (operador.equals("=="))
			return esq.doubleValue() == dir.doubleValue();
		else if (operador.equals("!="))
			return esq.doubleValue() != dir.doubleValue();
		throw new UnsupportedOperationException();
	}
}
