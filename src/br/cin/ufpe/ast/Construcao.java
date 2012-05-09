package br.cin.ufpe.ast;

import java.util.List;

import br.cin.ufpe.runtime.Acesso;
import br.cin.ufpe.runtime.Escopo;
import br.cin.ufpe.runtime.Objeto;

public class Construcao extends Expressao {

	private Expressao superClasse;
	private List<Expressao> initArgs;

	public Construcao(Expressao superClasse, List<Expressao> initArgs) {
		this.superClasse = superClasse;
		this.initArgs = initArgs;
	}

	@Override
	public Object valor(Escopo escopo) {
		br.cin.ufpe.runtime.Objeto rv = new Objeto();
		Object superClasse = this.superClasse.valor(escopo);
		rv.put(Acesso.SUPER, superClasse);
		Object[] args = new Object[initArgs.size() + 1];
		args[0] = rv;
		for (int i = 1; i < args.length; i++)
			args[i] = initArgs.get(i - 1).valor(escopo);
		Acesso.chamarMetodo(Acesso.INIT, args);
		return rv;
	}

}
