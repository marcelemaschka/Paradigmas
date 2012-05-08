package br.cin.ufpe.runtime.classes;

import br.cin.ufpe.runtime.Acesso;


@SuppressWarnings("serial")
public class ClasseDouble extends ClasseEmbutida {

	public static final ClasseDouble instancia = new ClasseDouble();

	private ClasseDouble() {
		put(Acesso.SUPER, ClasseNumber.instancia);

	}

	@Override
	public String getNome() {
		return "Double";
	}

}
