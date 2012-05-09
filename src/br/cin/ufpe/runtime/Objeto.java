package br.cin.ufpe.runtime;

import java.util.HashMap;

import br.cin.ufpe.runtime.classes.ClasseObjeto;

@SuppressWarnings("serial")
public class Objeto extends HashMap<Object, Object> {
	public Objeto() {
		put(Acesso.SUPER, ClasseObjeto.instancia);
	}
}
