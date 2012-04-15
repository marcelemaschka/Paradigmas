package br.cin.ufpe.ast;

import br.cin.ufpe.ast.Retornar.Retorno;

public class Se extends Comando {

	private Bloco bloco1;
	private Bloco bloco2;
	private Expressao expressao;

	public Se(Expressao expressao, Bloco bloco1, Bloco bloco2) {
		this.expressao = expressao;
		this.bloco1 = bloco1;
		this.bloco2 = bloco2;
		
	}

	@Override
	public void executarCmd(Escopo escopo) throws Retorno {
		Object valor = expressao.valor(escopo);
		if ((valor instanceof Boolean && valor.equals(Boolean.TRUE))
				|| (valor instanceof Number && ((Number) valor).doubleValue() != 0.0)
				|| (valor instanceof String && !valor.toString().trim()
						.equals(""))) {
			
			bloco1.executar(escopo);
		} else if (bloco2 != null) {
			bloco2.executar(escopo);
		}
	}
}
