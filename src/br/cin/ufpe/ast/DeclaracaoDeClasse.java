package br.cin.ufpe.ast;

import br.cin.ufpe.ast.Retornar.Retorno;
import br.cin.ufpe.runtime.Acesso;
import br.cin.ufpe.runtime.Escopo;

public class DeclaracaoDeClasse extends Comando {

	private Identificador nome;
	private Identificador sup;
	private Expressao obj;

	public DeclaracaoDeClasse(Identificador nome, Identificador sup,
			Expressao obj) {
		this.nome = nome;
		this.sup = sup;
		this.obj = obj;
	}

	@Override
	protected void executarCmd(Escopo escopo) throws Retorno {
		Object superClasse = sup.valor(escopo);
		br.cin.ufpe.runtime.Objeto obj = (br.cin.ufpe.runtime.Objeto) this.obj
				.valor(escopo);
		obj.put(Acesso.SUPER, superClasse);
		escopo.put(nome.getNome(), obj);
	}

}
