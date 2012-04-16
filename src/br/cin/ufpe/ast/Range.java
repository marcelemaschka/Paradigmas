package br.cin.ufpe.ast;

public class Range extends Expressao {

	private Inteiro inicio;
	private Inteiro fim;
	private Inteiro passo;

	public Range(Inteiro inicio, Inteiro fim, Inteiro passo) {
		this.inicio = inicio;
		this.fim = fim;
		this.passo = passo;
	}

	@Override
	public Object valor(Escopo escopo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String valorTexto(Escopo escopo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
