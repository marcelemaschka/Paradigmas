package br.cin.ufpe.runtime.classes;

import java.util.List;

import br.cin.ufpe.runtime.Acesso;
import br.cin.ufpe.runtime.Funcao;
import br.cin.ufpe.runtime.Operacao;

@SuppressWarnings("serial")
public class ClasseLong extends ClasseEmbutida {

	public static final ClasseLong instancia = new ClasseLong();

	private ClasseLong() {
		put(Acesso.SUPER, ClasseNumber.instancia);
		put("#&#", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object a1 = args.get(0);
				Object a2 = args.get(1);
				if (!(a2 instanceof Long))
					throw Operacao.erroNaoImplementado("&", a1, a2);
				return (Long) a1 & (Long) a2;
			}
		});
		put("#|#", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object a1 = args.get(0);
				Object a2 = args.get(1);
				if (!(a2 instanceof Long))
					throw Operacao.erroNaoImplementado("|", a1, a2);
				return (Long) a1 | (Long) a2;
			}
		});
		put("#<<#", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object a1 = args.get(0);
				Object a2 = args.get(1);
				if (!(a2 instanceof Long))
					throw Operacao.erroNaoImplementado("<<", a1, a2);
				return (Long) a1 << (Long) a2;
			}
		});
		put("#>>#", new Funcao() {
			@Override
			public Object chamar(List<Object> args) {
				checarNumeroDeArgs(args, 2);
				Object a1 = args.get(0);
				Object a2 = args.get(1);
				if (!(a2 instanceof Long))
					throw Operacao.erroNaoImplementado(">>", a1, a2);
				return (Long) a1 >> (Long) a2;
			}
		});
	}

	@Override
	public String getNome() {
		return "Inteiro";
	}

}
