package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Escopo;

public class ExpressaoUnaria extends Expressao {

	private Expressao operando;
	private String operador;

	public ExpressaoUnaria(String operador, Expressao operando) {
		this.operador = operador;
		this.operando = operando;		
	}

	@Override
	public Object valor(Escopo escopo) {
		if (operador.equals("-")) {
			Number operando = (Number) this.operando.valor(escopo);
			return -operando.doubleValue();
		} else if (operador.equals("!")){
			Boolean operando = (Boolean) this.operando.valor(escopo);
			return !operando;
		}
			
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
