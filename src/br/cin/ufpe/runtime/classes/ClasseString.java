package br.cin.ufpe.runtime.classes;

import java.util.List;

import br.cin.ufpe.runtime.Chamada;
import br.cin.ufpe.runtime.Funcao;
import br.cin.ufpe.runtime.OperadorBinario;

@SuppressWarnings("serial")
public class ClasseString extends ClasseEmbutida {

	public static final ClasseString instancia = new ClasseString();

	private ClasseString() {
		put("classe", ClasseObjeto.instancia);
		put("comparar", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object a1 = args.get(0);
				Object a2 = args.get(1);
				if (!(a2 instanceof String))
					throw OperadorBinario.erroNaoImplementado("comparaçao", a1,
							a2);
				return ((String) a1).compareTo((String) a2);
			}
		});
		put("+", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				return Chamada.chamarMetodo("texto", args.get(0)).toString()
						+ Chamada.chamarMetodo("texto", args.get(1)).toString();
			}
		});
		put("*", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				String a1 = (String) args.get(0);
				Object a2 = args.get(1);
				if (!(a2 instanceof Number))
					throw OperadorBinario.erroNaoImplementado("*", a1, a2);
				return OperadorBinario.multiplicacaoString(a1,
						((Number) a2).longValue());
			}
		});
	}

	@Override
	public String getNome() {
		return "String";
	}
}
