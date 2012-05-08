package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Acesso;
import br.cin.ufpe.runtime.Escopo;

public class AcessoAtributo extends Expressao {

	private Expressao alvo;
	private Expressao arg;

	public AcessoAtributo(Expressao alvo, Expressao arg) {
		this.alvo = alvo;
		if (arg instanceof Identificador)
			this.arg = new Texto(((Identificador) arg).getNome());
		else
			this.arg = arg;
	}

	public Expressao getArg() {
		return arg;
	}

	public Expressao getAlvo() {
		return alvo;
	}

	@Override
	public Object valor(Escopo escopo) {
		Object alvo = this.alvo.valor(escopo);
		return Acesso.chamarMetodo(Acesso.GETTER, alvo, arg.valor(escopo));
	}
}
