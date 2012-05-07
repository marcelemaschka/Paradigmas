package br.cin.ufpe.runtime.classes;


@SuppressWarnings("serial")
public class ClasseDouble extends ClasseEmbutida {

	public static final ClasseDouble instancia = new ClasseDouble();

	private ClasseDouble() {
		put("classe", ClasseNumber.instancia);

	}

	@Override
	public String getNome() {
		return "Double";
	}

}
