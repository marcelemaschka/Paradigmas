package br.cin.ufpe.ast;

import br.cin.ufpe.ast.Retornar.Retorno;
import br.cin.ufpe.runtime.Escopo;

public abstract class Expressao extends Comando {

	public abstract Object valor(Escopo escopo);

	public abstract String valorTexto(Escopo escopo);
	
	@Override
	public void executar(Escopo escopo) throws Retorno {
		// variavel auxiliar para armazenar o valor da �ltimo comando/expressao
		// no interpretador interativo
		escopo.put("*_", valor(escopo)); 	
	}
	
	@Override
	public void executarCmd(Escopo escopo) {
	}	
}
