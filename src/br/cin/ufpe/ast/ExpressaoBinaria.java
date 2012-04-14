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
	public Object valor(Escopo escopo) {
		Object esq = esquerda.valor(escopo);
		Object dir = direita.valor(escopo);
		if ((esq instanceof String) || (dir instanceof String))
			return valorTexto(esquerda, direita, escopo);
		else if (esq instanceof Number)
			return valorNumerico((Number) esq, dir);

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

	private Object valorTexto(Expressao esquerda, Expressao direita,
			Escopo escopo) {

		if (operador.equals("+"))
			return esquerda.valorTexto(escopo) + direita.valorTexto(escopo);
		// TODO Implementar operador de igualdade '==' para Strings
		// Opcionalmente colocar suporte para '*' (multiplica��o de String
		// por n�mero)
		else if (operador.equals("==")) {
			return textoIgual(esquerda, direita, escopo);
		}else if(operador.equals("!=")){
			return !textoIgual(esquerda, direita, escopo);
		}
		throw new UnsupportedOperationException();
	}

	private boolean textoIgual(Expressao esquerda, Expressao direita,
			Escopo escopo) {
		if (esquerda.valor(escopo) instanceof String
				&& direita.valor(escopo) instanceof String) {
			return esquerda == null ? direita == null : esquerda
					.valorTexto(escopo).equals(direita.valorTexto(escopo));
		}else{
			return false;
		}
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

	@Override
	public String valorTexto(Escopo escopo) {
		Object valor = valor(escopo);
		if (valor instanceof Boolean) {
			return (Boolean) valor ? "verdadeiro" : "falso";
		}
		return valor.toString();
	}
}
