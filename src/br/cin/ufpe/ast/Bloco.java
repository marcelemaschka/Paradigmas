package br.cin.ufpe.ast;

import java.util.List;

public class Bloco extends Nodo {

	private List<Comando> comandos;

	public Bloco(List<Comando> comandos) {
		this.comandos = comandos;
	}

	public void executar(Escopo escopo) {
		for (Comando comando : comandos)
			comando.executar(escopo);
	}
}
