package br.cin.ufpe.runtime;

import java.util.HashMap;

import br.cin.ufpe.runtime.funcoes.FuncaoEmbutida;

@SuppressWarnings("serial")
public class Escopo extends HashMap<String, Object> {

	private Escopo superEscopo;

	public Escopo() {
		this(null);
	}

	public Escopo(Escopo superEscopo) {
		this.superEscopo = superEscopo;
	}

	public void embutirFuncao(FuncaoEmbutida func) {
		this.put(func.getNome(), func);
	}

	@Override
	public Object get(Object key) {
		if (containsKey(key))
			return super.get(key);
		else if (superEscopo != null)
			return superEscopo.get(key);
		return null;
	}

	@Override
	public Object put(String key, Object value) {
		if (superEscopo != null && superEscopo.get(key) != null) {
			return superEscopo.put(key, value);
		}
		return super.put(key, value);
	}

}