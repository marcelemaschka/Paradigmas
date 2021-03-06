package br.cin.ufpe.ast;

import java.util.List;

import br.cin.ufpe.runtime.Closure;
import br.cin.ufpe.runtime.Escopo;


public class Funcao extends Expressao {

	private List<String> parametros;
	private Bloco bloco;

	public Funcao(List<String> parametros, Bloco bloco) {
		this.parametros = parametros;
		this.bloco = bloco;
	}

	@Override
	public Object valor(Escopo escopo) {
		return new Closure(escopo, parametros, bloco);
	}

}
