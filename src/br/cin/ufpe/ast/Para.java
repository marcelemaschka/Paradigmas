package br.cin.ufpe.ast;

import br.cin.ufpe.ast.Retornar.Retorno;

public class Para extends Comando {

	private Identificador id;
	private Expressao expressao;
	private Bloco bloco;

	public Para(Identificador id, Expressao expressao, Bloco bloco) {
		this.id = id;
		this.expressao = expressao;
		this.bloco = bloco;
	}

	@Override
	public void executarCmd(Escopo escopo) throws Retorno {
		Object expVal = expressao.valor(escopo);
		if (!(expVal instanceof Iterable<?>))
			throw new IllegalArgumentException("Expressão deve ser iterável");
		@SuppressWarnings("unchecked")
		Iterable<Object> it = (Iterable<Object>) expVal;
		for (Object object : it) {
			escopo.put(id.getNome(), object);
			bloco.executar(escopo);
		}
	}
}
