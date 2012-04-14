package br.cin.ufpe.interpretador;

import java.io.IOException;
import java.util.Scanner;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import br.cin.ufpe.PortujavaLexer;
import br.cin.ufpe.PortujavaParser;
import br.cin.ufpe.ast.Comando;
import br.cin.ufpe.ast.Escopo;
import br.cin.ufpe.ast.Expressao;
import br.cin.ufpe.ast.Retornar.Retorno;

public class InterpretadorInterativo {
	
	public static void main(String[] args) throws IOException,
			RecognitionException, Retorno {

		System.out.println("Interpretador interativo da linguagem Portujava");
		System.out.println("Digite um comando ou expressão:");
		System.out.print(">>>");
		Escopo escopo = new Escopo();
		Scanner scanner = new Scanner(System.in);
		String line = "";
		while (!line.equals("sair")) {
			line = scanner.nextLine();			
			ANTLRStringStream cod = new ANTLRStringStream(line);
			PortujavaLexer lexer = new PortujavaLexer(cod);
			PortujavaParser parser = new PortujavaParser(new CommonTokenStream(
					lexer));
			Comando cmd = parser.comando();
			if (cmd instanceof Expressao)
				System.out.println(((Expressao) cmd).valor(escopo));
			else
				cmd.executar(escopo);	
			System.out.print(">>>");
		}
	}
}
