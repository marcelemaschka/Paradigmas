package br.cin.ufpe.runtime.funcoes;

import br.cin.ufpe.runtime.Closure;
import br.cin.ufpe.runtime.Escopo;

public abstract class FuncaoEmbutida extends Closure {

	public FuncaoEmbutida(Escopo escopo) {
		super(escopo, null, null);
	}

	public abstract String getNome();
}
