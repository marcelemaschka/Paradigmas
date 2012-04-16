package br.cin.ufpe.runtime.funcoes;

import java.io.OutputStream;

import br.cin.ufpe.runtime.Escopo;

public class EscreverLn extends Escrever {

	public EscreverLn(OutputStream saidaPadrao, OutputStream erroPadrao,
			Escopo escopo) {
		super(saidaPadrao, erroPadrao, escopo);
	}
	
	@Override
	public String getNome() {
		return "escrevaln";
	}

	@Override
	protected String filtrar(String texto) {
		return texto + "\n";
	}
}
