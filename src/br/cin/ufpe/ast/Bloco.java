package br.cin.ufpe.ast;

import java.util.List;

import br.cin.ufpe.ast.Retornar.Retorno;

public class Bloco extends Nodo {

	private List<Comando> comandos;

	public Bloco(List<Comando> comandos) {
		this.comandos = comandos;
	}
	
	
	
	protected Escopo configurarEscopo(Escopo escopo){
		return new Escopo(escopo);
	}

	public void executar(Escopo escopo) throws Retorno {
		
		Escopo escopoLocal=configurarEscopo(escopo);
		
		for (Comando comando : comandos)
			comando.executar(escopoLocal);
	}
}
