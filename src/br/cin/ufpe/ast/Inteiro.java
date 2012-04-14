package br.cin.ufpe.ast;

public class Inteiro extends Expressao {

	private long valor;

	public Inteiro(String text){
		valor = Long.parseLong(text);
	}
	
	@Override
	public Object valor() {
		return valor;
	}
}
