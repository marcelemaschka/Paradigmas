package br.cin.ufpe.ast;

import br.cin.ufpe.ast.Retornar.Retorno;
import br.cin.ufpe.runtime.Escopo;

public abstract class Comando extends Nodo {

	public void executar(Escopo escopo) throws Retorno {
		executarCmd(escopo);
		escopo.put("*_", null);
	}

	protected abstract void executarCmd(Escopo escopo) throws Retorno;
}
