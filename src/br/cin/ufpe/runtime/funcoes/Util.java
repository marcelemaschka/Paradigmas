package br.cin.ufpe.runtime.funcoes;

import java.io.InputStream;
import java.io.OutputStream;

import br.cin.ufpe.runtime.Escopo;
import br.cin.ufpe.runtime.classes.ClasseObjeto;

public class Util {

	public static void embutirFuncoes(Escopo escopo, InputStream entradaPadrao,
			OutputStream saidaPadrao, OutputStream erroPadrao) {
		escopo.embutirFuncao(new Escrever(saidaPadrao, erroPadrao, escopo));
		escopo.embutirFuncao(new EscreverLn(saidaPadrao, erroPadrao, escopo));
	}

	public static void embutirFuncoes(Escopo escopo) {
		embutirFuncoes(escopo, System.in, System.out, System.err);
	}

	public static void embutirClasses(Escopo escopo) {
		escopo.embutirClasse(ClasseObjeto.instancia);
	}
}
