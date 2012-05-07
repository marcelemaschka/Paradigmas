package br.cin.ufpe.runtime.classes;

import java.util.List;

import br.cin.ufpe.runtime.Chamada;
import br.cin.ufpe.runtime.Funcao;

@SuppressWarnings("serial")
public class ClasseObjeto extends ClasseEmbutida {

	public static final ClasseObjeto instancia = new ClasseObjeto();

	private ClasseObjeto() {
		put("texto", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 1);
				return args.get(0).toString();
			}
		});
		put("==", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object resultadoComparacao = null;
				try {
					resultadoComparacao = Chamada.chamarMetodo("comparar",
							args.get(0), args.get(1));
				} catch (Exception e) {
					return args.get(0).equals(args.get(1));
				}
				if (!(resultadoComparacao instanceof Number))
					throw new IllegalStateException(
							"Resultado de comparaçao deve ser um numero");
				return ((Number) resultadoComparacao).doubleValue() == 0;
			}
		});
		put("!=", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object resultadoComparacao = Chamada.chamarMetodo("comparar",
						args.get(0), args.get(1));
				if (!(resultadoComparacao instanceof Number))
					throw new IllegalStateException(
							"Resultado de comparaçao deve ser um numero");
				return ((Number) resultadoComparacao).doubleValue() != 0;
			}
		});
		put(">=", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object resultadoComparacao = Chamada.chamarMetodo("comparar",
						args.get(0), args.get(1));
				if (!(resultadoComparacao instanceof Number))
					throw new IllegalStateException(
							"Resultado de comparaçao deve ser um numero");
				return ((Number) resultadoComparacao).doubleValue() >= 0;
			}
		});
		put(">", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object resultadoComparacao = Chamada.chamarMetodo("comparar",
						args.get(0), args.get(1));
				if (!(resultadoComparacao instanceof Number))
					throw new IllegalStateException(
							"Resultado de comparaçao deve ser um numero");
				return ((Number) resultadoComparacao).doubleValue() > 0;
			}
		});
		put("<=", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object resultadoComparacao = Chamada.chamarMetodo("comparar",
						args.get(0), args.get(1));
				if (!(resultadoComparacao instanceof Number))
					throw new IllegalStateException(
							"Resultado de comparaçao deve ser um numero");
				return ((Number) resultadoComparacao).doubleValue() <= 0;
			}
		});
		put("<", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object resultadoComparacao = Chamada.chamarMetodo("comparar",
						args.get(0), args.get(1));
				if (!(resultadoComparacao instanceof Number))
					throw new IllegalStateException(
							"Resultado de comparaçao deve ser um numero");
				return ((Number) resultadoComparacao).doubleValue() < 0;
			}
		});
	}

	@Override
	public String getNome() {
		return "Object";
	}
}
