package br.cin.ufpe.runtime;

public class Operacao {

	public static Object binaria(Object esq, Object dir, String operador) {
		return Acesso.chamarMetodo("#" + operador + "#", esq, dir);
	}

	public static Object unaria(Object alvo, String operador) {
		return Acesso.chamarMetodo(operador + "#", alvo);
	}

	public static String multiplicacaoString(String s, long n) {
		StringBuilder sb = new StringBuilder();
		for (long i = 0; i < n; i++)
			sb.append(s);
		return sb.toString();
	}

	public static RuntimeException erroNaoImplementado(String operador,
			Object esq, Object dir) {
		return new IllegalArgumentException(
				String.format(
						"O operador '%s' não está implementado para os tipos '%s' e '%s'",
						operador, esq.getClass().getName(), dir.getClass()
								.getName()));
	}

}
