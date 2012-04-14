package br.cin.ufpe.ast;

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
		return escopo.get(nome);
	}

	@Override
	public String valorTexto(Escopo escopo) {
		Object valor=valor(escopo);
		if(valor instanceof Boolean){
			return (Boolean) valor?"verdadeiro":"falso";
		}
		return valor.toString();
	}
}
