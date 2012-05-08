package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Escopo;

public class Identificador extends Expressao {

	private String nome;
	private int nivel;

	public Identificador(String nome, int nivel) {
		this.nome = nome;
		this.nivel = nivel;
	}

	public String getNome() {
		return nome;
	}
	
	public int getNivel() {
		return nivel;
	}

	@Override
	public Object valor(Escopo escopo) {
		Escopo e = escopo;
		for (int i = 0; i < nivel && e.getSuperEscopo() != null; i++)
			e = e.getSuperEscopo();
		Object valor = e.get(nome);
		if (valor == null) {
			throw new IllegalArgumentException("Variável " + nome
					+ " não foi declarada");
		}
		return valor;
	}

	@Override
	public String toString() {
		return nome;
	}

}
