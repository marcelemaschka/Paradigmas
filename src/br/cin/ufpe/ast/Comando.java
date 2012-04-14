package br.cin.ufpe.ast;

public abstract class Comando extends Nodo {

	public abstract void executar(Escopo escopo);
}
