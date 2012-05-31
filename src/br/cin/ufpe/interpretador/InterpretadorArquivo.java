package br.cin.ufpe.interpretador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import br.cin.ufpe.PortujavaLexer;
import br.cin.ufpe.PortujavaParser;
import br.cin.ufpe.ast.Programa;
import br.cin.ufpe.ast.Retornar.Retorno;
import br.cin.ufpe.runtime.Escopo;
import br.cin.ufpe.runtime.funcoes.Util;

public class InterpretadorArquivo {
	
	public static void main(String[] args) throws IOException, Retorno {

		System.out.println("Interpretador de arquivo da linguagem Portujava");
		
		String nomeArquivo = "PortuJava.txt";
		
		FileInputStream fis = new FileInputStream(new File(nomeArquivo));
		
		
		ANTLRInputStream cod = new ANTLRInputStream(fis);

		Escopo escopo = new Escopo();
		Util.embutirFuncoes(escopo);
		Util.embutirClasses(escopo);

		PortujavaLexer lexer = new PortujavaLexer(cod);
		PortujavaParser parser = new PortujavaParser(new CommonTokenStream(
				lexer));
		try {
			Programa prog = parser.programa();
			if (prog != null) {
				prog.executar(escopo);
			}
		} catch (RecognitionException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		
		
	}


}
