package br.cin.ufpe.runtime;

import java.util.ArrayList;
import java.util.List;

import br.cin.ufpe.runtime.classes.ClasseBoolean;
import br.cin.ufpe.runtime.classes.ClasseDouble;
import br.cin.ufpe.runtime.classes.ClasseLong;
import br.cin.ufpe.runtime.classes.ClasseString;

public class Chamada {

	public static Object chamarMetodo(String nome, Object... argumentos) {
		Object alvo = argumentos[0];
		ArrayList<Object> args = new ArrayList<Object>();
		for (int i = 0; i < argumentos.length; i++)
			args.add(argumentos[i]);
		Objeto classe = null;
		if (alvo instanceof Boolean)
			classe = ClasseBoolean.instancia;
		else if (alvo instanceof Long)
			classe = ClasseLong.instancia;
		else if (alvo instanceof Double)
			classe = ClasseDouble.instancia;
		else if (alvo instanceof String)
			classe = ClasseString.instancia;
		else if (alvo instanceof Objeto)
			classe = (Objeto) alvo;
		else
			throw new IllegalArgumentException("Tipo de objeto desconhecido");
		return chamarMetodoDeClasse(nome, args, classe);
	}

	private static Object chamarMetodoDeClasse(String nome, List<Object> args,
			Objeto classe) {
		Object func = null;
		Object cls = classe;
		while (func == null && cls instanceof Objeto) {
			func = ((Objeto) cls).get(nome);
			cls = ((Objeto) cls).get("classe");
		}
		if (!(func instanceof Funcao))
			throw new IllegalArgumentException(String.format(
					"Attributo '%s' não é uma função", nome));
		Funcao f = (Funcao) func;
		return f.chamar(args);
	}
}
