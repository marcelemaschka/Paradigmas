package br.cin.ufpe.ast;

import java.util.List;

import br.cin.ufpe.runtime.Closure;


public class ChamadaDeFuncao extends Expressao {

	private Expressao expressao;
	private Expressao args;

	public ChamadaDeFuncao(Expressao expressao, Expressao args) {
		this.expressao = expressao;
		this.args = args;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object valor(Escopo escopo) {
		Object alvo = null;
		if (expressao instanceof Identificador)
			alvo = ((Identificador) expressao).valor(escopo);
		if (!(alvo instanceof Closure))
			throw new UnsupportedOperationException(
					"Só é possível chamar funções");
		Closure closure = (Closure) alvo;
		return closure.chamar((List<Object>) args.valor(escopo));
	}

	@Override
	public String valorTexto(Escopo escopo) {
		// TODO Auto-generated method stub
		return null;
	}
}
