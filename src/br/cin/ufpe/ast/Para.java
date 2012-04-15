package br.cin.ufpe.ast;

import br.cin.ufpe.ast.Retornar.Retorno;

public class Para extends Comando{

	
	public Para(Atribuicao atribuicaoInicial, Expressao expressaoComparacao,
			Comando comando, Bloco bloco) {
		super();
		this.atribuicaoInicial = atribuicaoInicial;
		this.expressaoComparacao = expressaoComparacao;
		this.comando = comando;
		this.bloco = bloco;
	}

	private Atribuicao atribuicaoInicial;
	
	private Expressao expressaoComparacao;
	
	private Comando comando;
	
	private Bloco bloco;

	@Override
	public void executar(Escopo escopo) throws Retorno {
		
		atribuicaoInicial.executar(escopo);
		while((Boolean)expressaoComparacao.valor(escopo)==true){
			bloco.executar(escopo);
			comando.executar(escopo);
		}
		
	}
}
