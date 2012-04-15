package br.cin.ufpe.ast;

import java.util.List;

public class Programa extends Bloco {

	public Programa(List<Comando> comandos) {
		super(comandos);		
	}

	@Override
	protected Escopo configurarEscopo(Escopo escopo) {
	
		return escopo;
	}
	
	

}
