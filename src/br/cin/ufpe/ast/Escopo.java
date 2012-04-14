package br.cin.ufpe.ast;

import java.util.HashMap;

@SuppressWarnings("serial")
public class Escopo extends HashMap<String, Object> {

	private Escopo superEscopo;

	public Escopo() {
		this(null);
	}

	public Escopo(Escopo superEscopo) {
		this.superEscopo = superEscopo;
	}

	@Override
	public Object get(Object key) {
		if (containsKey(key))
			return super.get(key);
		else if (superEscopo != null)
			return superEscopo.get(key);
		return null;
	}
}
