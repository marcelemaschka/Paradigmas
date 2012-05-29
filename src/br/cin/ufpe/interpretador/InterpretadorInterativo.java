package br.cin.ufpe.interpretador;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import br.cin.ufpe.PortujavaLexer;
import br.cin.ufpe.PortujavaParser;
import br.cin.ufpe.ast.Programa;
import br.cin.ufpe.ast.Retornar.Retorno;
import br.cin.ufpe.runtime.Escopo;
import br.cin.ufpe.runtime.funcoes.Util;

public class InterpretadorInterativo {

	public static void main(String[] args) throws IOException, Retorno {

		System.out.println("Interpretador interativo da linguagem Portujava");
		System.out.println("Digite um comando ou expressão:");
		System.out.print(">>>");
		Escopo escopo = new Escopo();
		Util.embutirFuncoes(escopo);
		Util.embutirClasses(escopo);		
		Scanner scanner = new Scanner(System.in);

		while (true) {
			String line = null;
			try {
				line = scanner.nextLine();
				if(!line.trim().endsWith(";")){
					line = line+";";
				}
			} catch (NoSuchElementException e) {
				System.exit(0);
			}
			if (line == null)
				continue;
			if (line.equals("sair"))
				System.exit(0);
			
			try{
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
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			} 
			catch(Exception e){
				e.printStackTrace();
			}
			System.out.print(">>>");
			
		}
	}

}
