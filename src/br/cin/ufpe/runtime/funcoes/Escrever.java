package br.cin.ufpe.runtime.funcoes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import br.cin.ufpe.runtime.Escopo;

public class Escrever extends FuncaoEmbutida {

	private OutputStream saidaPadrao;
	private OutputStream erroPadrao;

	public Escrever(OutputStream saidaPadrao, OutputStream erroPadrao,
			Escopo escopo) {
		this.saidaPadrao = saidaPadrao;
		this.erroPadrao = erroPadrao;
	}

	@Override
	public String getNome() {
		return "escreva";
	}

	@Override
	public Object chamar(List<Object> args) {
		if (args.size() < 2)
			throw new IllegalArgumentException(
					"Funcao 'escreva' precisa de pelo menos um argumento");
		String txt = args.get(1).toString();

		OutputStream s = saidaPadrao;
		if (args.size() > 2) {
			Object saida = args.get(2);
			if (saida instanceof Long && saida.equals(2L))
				s = erroPadrao;
			else
				try {
					s = new FileOutputStream(saida.toString());
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				}
		}
		PrintStream ps = new PrintStream(s);
		ps.print(filtrar(txt));
		ps.flush();
		return null;
	}

	protected String filtrar(String texto) {
		return texto;
	}

}
