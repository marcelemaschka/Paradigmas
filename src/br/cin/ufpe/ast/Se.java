package br.cin.ufpe.ast;

import br.cin.ufpe.ast.Retornar.Retorno;

public class Se extends Comando {

	private Bloco bloco;
	private Expressao expressao;

	public Se(Expressao expressao, Bloco bloco) {
		this.expressao = expressao;
		this.bloco = bloco;
	}

	@Override
	public void executar(Escopo escopo) throws Retorno {
		Object valor = expressao.valor(escopo);
		if ((valor instanceof Boolean && valor.equals(Boolean.TRUE))
				|| (valor instanceof Number && ((Number) valor).doubleValue() != 0.0)
				|| (valor instanceof String && !valor.toString().trim()
						.equals("")))
			bloco.executar(escopo);
		// TODO Implementar else/elseif
	}
}
