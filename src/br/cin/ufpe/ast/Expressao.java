package br.cin.ufpe.ast;

public abstract class Expressao extends Comando {

	public abstract Object valor(Escopo escopo);

	@Override
	public void executar(Escopo escopo) {
	}
}
