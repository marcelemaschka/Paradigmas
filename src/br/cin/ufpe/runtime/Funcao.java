package br.cin.ufpe.runtime;

import java.util.List;

public abstract class Funcao {
	public abstract Object chamar(List<Object> args);

	@Override
	public String toString() {
		return "<<Função>>";
	}

	protected IllegalArgumentException argInvalido() {
		return new IllegalArgumentException(
				"Função chamada com número de argumentos inesperado.");
	}

	protected void checarNumeroDeArgs(List<Object> args, int n) {
		if (args.size() != n)
			throw argInvalido();
	}
}
