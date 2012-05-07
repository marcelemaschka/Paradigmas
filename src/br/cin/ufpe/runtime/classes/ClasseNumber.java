package br.cin.ufpe.runtime.classes;

import java.util.List;

import br.cin.ufpe.runtime.Chamada;
import br.cin.ufpe.runtime.Funcao;
import br.cin.ufpe.runtime.OperadorBinario;

@SuppressWarnings("serial")
public class ClasseNumber extends ClasseEmbutida {

	public static final ClasseNumber instancia = new ClasseNumber();

	private ClasseNumber() {
		put("classe", ClasseObjeto.instancia);
		put("comparar", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object a1 = args.get(0);
				Object a2 = args.get(1);
				if (!(a2 instanceof Number))
					throw OperadorBinario.erroNaoImplementado("comparaçao", a1,
							a2);
				return ((Number) a1).doubleValue()
						- ((Number) a2).doubleValue();
			}
		});
		put("+", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object a1 = args.get(0);
				Object a2 = args.get(1);
				boolean resultadoInteiro = a1 instanceof Long
						&& a2 instanceof Long;
				if (a2 instanceof String)
					return Chamada.chamarMetodo("texto", a1).toString()
							+ Chamada.chamarMetodo("texto", a2).toString();
				if (!(a2 instanceof Number))
					throw OperadorBinario.erroNaoImplementado("+", a1, a2);
				Number rv = ((Number) a1).doubleValue()
						+ ((Number) a2).doubleValue();
				if (resultadoInteiro)
					return rv.longValue();
				return rv.doubleValue();

			}
		});
		put("-", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object a1 = args.get(0);
				Object a2 = args.get(1);
				boolean resultadoInteiro = a1 instanceof Long
						&& a2 instanceof Long;
				if (!(a2 instanceof Number))
					throw OperadorBinario.erroNaoImplementado("-", a1, a2);
				Number rv = ((Number) a1).doubleValue()
						- ((Number) a2).doubleValue();
				if (resultadoInteiro)
					return rv.longValue();
				return rv.doubleValue();
			}
		});
		put("*", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object a1 = args.get(0);
				Object a2 = args.get(1);
				boolean resultadoInteiro = a1 instanceof Long
						&& a2 instanceof Long;
				if (a2 instanceof String)
					return OperadorBinario.multiplicacaoString((String) a1,
							((Number) a1).longValue());
				if (!(a2 instanceof Number))
					throw OperadorBinario.erroNaoImplementado("*", a1, a2);
				Number rv = ((Number) a1).doubleValue()
						* ((Number) a2).doubleValue();
				if (resultadoInteiro)
					return rv.longValue();
				return rv.doubleValue();
			}

		});
		put("/", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object a1 = args.get(0);
				Object a2 = args.get(1);
				boolean resultadoInteiro = a1 instanceof Long
						&& a2 instanceof Long;
				if (!(a2 instanceof Number))
					throw OperadorBinario.erroNaoImplementado("/", a1, a2);
				Number rv = ((Number) a1).doubleValue()
						/ ((Number) a2).doubleValue();
				if (resultadoInteiro)
					return rv.longValue();
				return rv.doubleValue();
			}
		});
	}

	@Override
	public String getNome() {
		return "Number";
	}

}
