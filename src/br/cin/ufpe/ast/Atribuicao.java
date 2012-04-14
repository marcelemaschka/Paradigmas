package br.cin.ufpe.ast;

public class Atribuicao extends Comando {

	private Identificador identificador;
	private Expressao expressao;

	public Atribuicao(Identificador identificador, Expressao expressao) {
		this.identificador = identificador;
		this.expressao = expressao;
	}

	@Override
	public void executar(Escopo escopo) {
		escopo.put(identificador.getNome(), expressao.valor(escopo));
	}
}
