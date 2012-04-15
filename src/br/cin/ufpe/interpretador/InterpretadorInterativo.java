package br.cin.ufpe.interpretador;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import br.cin.ufpe.PortujavaLexer;
import br.cin.ufpe.PortujavaParser;
import br.cin.ufpe.ast.Escopo;
import br.cin.ufpe.ast.Programa;
import br.cin.ufpe.ast.Retornar.Retorno;

public class InterpretadorInterativo {

	public static void main(String[] args) throws IOException, Retorno {

		System.out.println("Interpretador interativo da linguagem Portujava");
		System.out.println("Digite um comando ou expressão:");
		System.out.print(">>>");
		Escopo escopo = new Escopo();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			String line = null;
			try {
				line = scanner.nextLine();
			} catch (NoSuchElementException e) {
				System.exit(0);
			}
			if (line == null)
				continue;
			if (line.equals("sair"))
				System.exit(0);
			ANTLRStringStream cod = new ANTLRStringStream(line);
			PortujavaLexer lexer = new PortujavaLexer(cod);
			PortujavaParser parser = new PortujavaParser(new CommonTokenStream(
					lexer));
			try {
				Programa prog = parser.programa();
				if (prog != null) {
					prog.executar(escopo);
					Object valor = escopo.get("*_");
					if (valor != null)
						System.out.println(valor);
				}
			} catch (RecognitionException e) {
				e.printStackTrace();
			}
			System.out.print(">>>");
		}
	}

}
