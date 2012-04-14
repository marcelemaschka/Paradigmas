package br.cin.ufpe.ast;

import br.cin.ufpe.ast.Retornar.Retorno;

public abstract class Comando extends Nodo {

	public abstract void executar(Escopo escopo) throws Retorno;
}
