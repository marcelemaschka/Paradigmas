package br.cin.ufpe.ast;

import br.cin.ufpe.ast.Retornar.Retorno;

public class Escreva extends Comando {

	public Escreva(Expressao expressao){
		this.expressao=expressao;
	}
	
	private Expressao expressao ;
	
	@Override
	protected void executarCmd(Escopo escopo) throws Retorno {
		
		System.out.println(expressao.valorTexto(escopo));
	}

}
