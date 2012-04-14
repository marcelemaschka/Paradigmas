package br.cin.ufpe.ast;

public class ExpressaoUnaria extends Expressao {

	private Expressao operando;
	private String operador;

	public ExpressaoUnaria(String operador, Expressao operando) {
		this.operador = operador;
		this.operando = operando;		
	}

	@Override
	public Object valor(Escopo escopo) {
		Number operando = (Number) this.operando.valor(escopo);
		if (operador.equals("-"))
			return -operando.doubleValue();
		throw new UnsupportedOperationException();
	}

	@Override
	public String valorTexto(Escopo escopo) {
		Object valor=valor(escopo);
		if(valor instanceof Boolean){
			return (Boolean) valor?"verdadeiro":"falso";
		}
		return valor.toString();
	}

}
