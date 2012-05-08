package br.cin.ufpe.ast;

import java.util.HashMap;

import br.cin.ufpe.runtime.Escopo;

public class Objeto extends Expressao {

	private HashMap<String, Expressao> obj;

	public Objeto() {
		obj = new HashMap<String, Expressao>();
	}

	public void add(String k, Expressao v) {
		obj.put(k, v);
	}

	@Override
	public Object valor(Escopo escopo) {
		br.cin.ufpe.runtime.Objeto rv = new br.cin.ufpe.runtime.Objeto();
		for (String k : obj.keySet())
			rv.put(k, obj.get(k).valor(escopo));
		return rv;
	}
}
