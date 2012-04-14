package br.cin.ufpe.ast;

import java.util.List;

import br.cin.ufpe.ast.Retornar.Retorno;

public class Funcao extends Expressao {

	private List<String> parametros;
	private Bloco bloco;

	public Funcao(List<String> parametros, Bloco bloco) {
		this.parametros = parametros;
		this.bloco = bloco;
	}

	@Override
	public Object valor(Escopo escopo) {
		return new Closure(escopo, parametros, bloco);
	}

	public static class Closure {
		private Escopo superEscopo;
		private List<String> parametros;
		private Bloco bloco;

		public Closure(Escopo superEscopo, List<String> parametros, Bloco bloco) {
			this.superEscopo = superEscopo;
			this.parametros = parametros;
			this.bloco = bloco;
		}

		public Object chamar(List<Object> args) {
			int len = args.size();
			if (len != parametros.size())
				throw new IllegalArgumentException(
						"Função chamada com número de argumentos diferente da declaração.");
			// o primeiro passo é atribuir os valores dos argumentos
			// a seus respectivos identificadores
			Escopo escopo = new Escopo(superEscopo);
			for (int i = 0; i < len; i++)
				escopo.put(parametros.get(i), args.get(i));
			Object valorRetorno = null;
			try {
				bloco.executar(escopo);
			} catch (Retorno e) {
				valorRetorno = e.getValor();
			}
			return valorRetorno;
		}
	}

	@Override
	public String valorTexto(Escopo escopo) {
		// TODO Auto-generated method stub
		return null;
	}
}
