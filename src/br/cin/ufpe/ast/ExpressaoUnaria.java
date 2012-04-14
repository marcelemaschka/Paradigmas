package br.cin.ufpe.ast;

public class ExpressaoUnaria extends Expressao {

	private Expressao operando;
	private String operador;

	public ExpressaoUnaria(String operador, Expressao operando) {
		this.operador = operador;
		this.operando = operando;		
	}

	@Override
	public Object valor() {
		Number operando = (Number) this.operando.valor();
		if (operador.equals("-"))
			return -operando.doubleValue();
		throw new UnsupportedOperationException();
	}

}
