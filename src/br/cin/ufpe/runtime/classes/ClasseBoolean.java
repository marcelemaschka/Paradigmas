package br.cin.ufpe.runtime.classes;

import java.util.List;

import br.cin.ufpe.runtime.Acesso;
import br.cin.ufpe.runtime.Funcao;
import br.cin.ufpe.runtime.Operacao;

@SuppressWarnings("serial")
public class ClasseBoolean extends ClasseEmbutida {

	public static final ClasseBoolean instancia = new ClasseBoolean();

	private ClasseBoolean() {
		put(Acesso.SUPER, ClasseObjeto.instancia);
		put("texto", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 1);
				return ((Boolean) args.get(0)).booleanValue() ? "verdadeiro"
						: "falso";
			}
		});
		put("comparar", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object a1 = args.get(0);
				Object a2 = args.get(1);
				if (!(a2 instanceof Boolean))
					throw Operacao.erroNaoImplementado("comparaçao", a1, a2);
				return ((Boolean) a1).compareTo((Boolean) a2);
			}
		});
		put("#+#", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object a1 = args.get(0);
				Object a2 = args.get(1);
				if (a2 instanceof String)
					return Acesso.chamarMetodo("texto", a1).toString()
							+ Acesso.chamarMetodo("texto", a2).toString();
				throw Operacao.erroNaoImplementado("+", a1, a2);
			}
		});
		put("#&&#", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object a1 = args.get(0);
				Object a2 = args.get(1);
				if (!(a2 instanceof Boolean))
					throw Operacao.erroNaoImplementado("&&", a1, a2);
				return (Boolean) a1 && (Boolean) a2;
			}
		});
		put("#||#", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object a1 = args.get(0);
				Object a2 = args.get(1);
				if (!(a2 instanceof Boolean))
					throw Operacao.erroNaoImplementado("||", a1, a2);
				return (Boolean) a1 || (Boolean) a2;
			}
		});
		put("!#", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 1);
				return !((Boolean) args.get(0)).booleanValue();
			}
		});
	}

	@Override
	public String getNome() {
		return "Booleano";
	}
}
