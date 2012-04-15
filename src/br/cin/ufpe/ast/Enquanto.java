package br.cin.ufpe.ast;

import br.cin.ufpe.ast.Retornar.Retorno;

public class Enquanto extends Comando {

	private Bloco bloco;
	private Expressao expressao;

	public Enquanto(Expressao expressao, Bloco bloco) {
		this.expressao = expressao;
		this.bloco = bloco;
	}

	@Override
	public void executarCmd(Escopo escopo) throws Retorno {
		Object valor = expressao.valor(escopo);
		while ((valor instanceof Boolean && valor.equals(Boolean.TRUE))
				|| (valor instanceof Number && ((Number) valor).doubleValue() != 0.0)
				|| (valor instanceof String && !valor.toString().trim()
						.equals(""))) {
			bloco.executar(escopo);
			valor = expressao.valor(escopo);
		}
	}
}
