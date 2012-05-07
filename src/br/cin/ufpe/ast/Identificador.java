package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Escopo;

public class Identificador extends Expressao {

	private String nome;

	public Identificador(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}

	@Override
	public Object valor(Escopo escopo) {
		Object valor= escopo.get(nome);
		if(valor==null){
			throw new IllegalArgumentException("Variável "+nome+" não foi declarada");
		}
		return valor;
	}

}
