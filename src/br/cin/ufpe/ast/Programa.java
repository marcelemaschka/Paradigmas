package br.cin.ufpe.ast;

import java.util.List;

import br.cin.ufpe.runtime.Escopo;

public class Programa extends Bloco {

	public Programa(List<Comando> comandos) {
		super(comandos);		
	}

	@Override
	protected Escopo configurarEscopo(Escopo escopo) {
	
		return escopo;
	}
	
	

}
