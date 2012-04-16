package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Escopo;

public class Atribuicao extends Comando {

	private Identificador identificador;
	private Expressao expressao;

	public Atribuicao(Identificador identificador, Expressao expressao) {
		this.identificador = identificador;
		this.expressao = expressao;
	}

	@Override
	public void executarCmd(Escopo escopo) {
		escopo.put(identificador.getNome(), expressao.valor(escopo));
	}
}
