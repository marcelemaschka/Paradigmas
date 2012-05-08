package br.cin.ufpe.ast;

import java.util.ArrayList;
import java.util.List;

import br.cin.ufpe.ast.Retornar.Retorno;
import br.cin.ufpe.runtime.Escopo;

public class Bloco extends Nodo {

	private List<Comando> comandos;

	public Bloco(Comando comando) {
		this.comandos = new ArrayList<Comando>();
		this.comandos.add(comando);
	}

	public Bloco(List<Comando> comandos) {
		this.comandos = comandos;
	}

	public void executar(Escopo escopo) throws Retorno {
		for (Comando comando : comandos)
			comando.executar(escopo);
	}
}
