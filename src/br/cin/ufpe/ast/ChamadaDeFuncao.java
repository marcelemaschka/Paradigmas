package br.cin.ufpe.ast;

import java.util.ArrayList;
import java.util.List;

import br.cin.ufpe.runtime.Closure;
import br.cin.ufpe.runtime.Escopo;

public class ChamadaDeFuncao extends Expressao {

	private Expressao expressao;
	private List<Expressao> args;

	public ChamadaDeFuncao(Expressao expressao, List<Expressao> args) {
		this.expressao = expressao;
		this.args = args;
	}
	
	@Override
	public Object valor(Escopo escopo) {
		Object alvo = null;
		if (expressao instanceof Identificador)
			alvo = ((Identificador) expressao).valor(escopo);
		if (!(alvo instanceof Closure))
			throw new UnsupportedOperationException(
					"Só é possível chamar funções");
		Closure closure = (Closure) alvo;
		ArrayList<Object> args = new ArrayList<Object>();
		for (Expressao exp : this.args)
			args.add(exp.valor(escopo));
		return closure.chamar(args);
	}

	@Override
	public String valorTexto(Escopo escopo) {
		// TODO Auto-generated method stub
		return null;
	}
}
