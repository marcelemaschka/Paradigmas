package br.cin.ufpe.ast;

import java.util.List;

import br.cin.ufpe.ast.Retornar.Retorno;

public class Bloco extends Nodo {

	private List<Comando> comandos;

	public Bloco(List<Comando> comandos) {
		this.comandos = comandos;
	}

	public void executar(Escopo escopo) throws Retorno {
		for (Comando comando : comandos)
			comando.executar(escopo);
	}
}
