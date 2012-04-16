package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Escopo;

public class Retornar extends Comando {

	private Expressao expressao;

	public Retornar(Expressao expressao) {
		this.expressao = expressao;
	}

	@Override
	public void executarCmd(Escopo escopo) throws Retorno {
		throw new Retorno(expressao.valor(escopo));
	}

	@SuppressWarnings("serial")
	public static class Retorno extends Throwable {
		private Object valor;

		public Retorno(Object valor) {
			this.valor = valor;
		}

		public Object getValor() {
			return valor;
		}
	}
}
