package br.cin.ufpe.runtime;

import java.util.ArrayList;

import br.cin.ufpe.runtime.classes.ClasseBoolean;
import br.cin.ufpe.runtime.classes.ClasseDouble;
import br.cin.ufpe.runtime.classes.ClasseLong;
import br.cin.ufpe.runtime.classes.ClasseString;

public class Acesso {

	public static final String GETTER = "[getAtributo]";
	public static final String SETTER = "[setAtributo]";
	public static final String SUPER = "[super]";
	public static final String INIT = "[init]";

	public static Object chamarMetodo(String nome, Object... argumentos) {
		Object alvo = argumentos[0];
		Object func = getAtributoReal(alvo, nome);
		if (!(func instanceof Funcao))
			throw new IllegalArgumentException(String.format(
					"Attributo '%s' não é uma função", nome));
		return chamarMetodo((Funcao) func, argumentos);
	}

	private static Object chamarMetodo(Funcao f, Object... argumentos) {
		ArrayList<Object> args = new ArrayList<Object>();
		for (int i = 0; i < argumentos.length; i++)
			args.add(argumentos[i]);
		return f.chamar(args);
	}

	public static Object getAtributoReal(Object alvo, Object arg) {
		Object cls = null;
		if (alvo.getClass() == Objeto.class)
			cls = alvo;
		else
			cls = getSuper(alvo);
		Object rv = null;
		while (rv == null && cls instanceof Objeto) {
			Objeto c = (Objeto) cls;
			rv = c.get(arg);
			cls = getSuper(cls);
		}
		return rv;
	}

	public static Object getSuper(Object alvo) {
		Object rv = null;
		if (alvo instanceof Boolean)
			rv = ClasseBoolean.instancia;
		else if (alvo instanceof Long)
			rv = ClasseLong.instancia;
		else if (alvo instanceof Double)
			rv = ClasseDouble.instancia;
		else if (alvo instanceof String)
			rv = ClasseString.instancia;
		else if (alvo instanceof Objeto)
			rv = (Objeto) ((Objeto) alvo).get(SUPER);
		else
			throw new IllegalArgumentException("Tipo de objeto desconhecido");
		return rv;
	}
}
