package br.cin.ufpe.runtime.classes;

import java.util.List;

import br.cin.ufpe.runtime.Acesso;
import br.cin.ufpe.runtime.Funcao;
import br.cin.ufpe.runtime.Objeto;

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
		put(Acesso.GETTER, new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object alvo = args.get(0);
				Object attr = args.get(1);				
				Object cls = null;
				if (alvo.getClass() == Objeto.class)
					cls = alvo;
				else
					cls = Acesso.getSuper(alvo);
				Object rv = null;
				while (rv == null && cls instanceof Objeto) {
					Objeto c = (Objeto) cls;
					rv = c.get(attr);
					cls = Acesso.getSuper(cls);
				}
				return rv;
			}
		});
		put(Acesso.SETTER, new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 3);
				((Objeto) args.get(0)).put(args.get(1), args.get(2));
				return null;
			}
		});
		put("#==#", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object resultadoComparacao = null;
				try {
					resultadoComparacao = Acesso.chamarMetodo("comparar",
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
		put("#!=#", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object resultadoComparacao = Acesso.chamarMetodo("comparar",
						args.get(0), args.get(1));
				if (!(resultadoComparacao instanceof Number))
					throw new IllegalStateException(
							"Resultado de comparaçao deve ser um numero");
				return ((Number) resultadoComparacao).doubleValue() != 0;
			}
		});
		put("#>=#", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object resultadoComparacao = Acesso.chamarMetodo("comparar",
						args.get(0), args.get(1));
				if (!(resultadoComparacao instanceof Number))
					throw new IllegalStateException(
							"Resultado de comparaçao deve ser um numero");
				return ((Number) resultadoComparacao).doubleValue() >= 0;
			}
		});
		put("#>#", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object resultadoComparacao = Acesso.chamarMetodo("comparar",
						args.get(0), args.get(1));
				if (!(resultadoComparacao instanceof Number))
					throw new IllegalStateException(
							"Resultado de comparaçao deve ser um numero");
				return ((Number) resultadoComparacao).doubleValue() > 0;
			}
		});
		put("#<=#", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object resultadoComparacao = Acesso.chamarMetodo("comparar",
						args.get(0), args.get(1));
				if (!(resultadoComparacao instanceof Number))
					throw new IllegalStateException(
							"Resultado de comparaçao deve ser um numero");
				return ((Number) resultadoComparacao).doubleValue() <= 0;
			}
		});
		put("#<#", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object resultadoComparacao = Acesso.chamarMetodo("comparar",
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
		return "Objeto";
	}
}
