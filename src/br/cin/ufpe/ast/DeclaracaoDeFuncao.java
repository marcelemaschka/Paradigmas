package br.cin.ufpe.ast;

import java.util.List;

public class DeclaracaoDeFuncao extends Atribuicao {

	public DeclaracaoDeFuncao(Identificador identificador,
			List<String> parametros, Bloco bloco) {
		super(identificador, new Funcao(parametros, bloco));
	}

}
