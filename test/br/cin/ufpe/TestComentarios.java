package br.cin.ufpe;

import static org.junit.Assert.assertEquals;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;

import br.cin.ufpe.ast.Escopo;
import br.cin.ufpe.ast.Programa;

public class TestComentarios {
	
	private Escopo escopo;
	
	public void executar(String codigo) throws RecognitionException {
		escopo = new Escopo();
		ANTLRStringStream cod = new ANTLRStringStream(codigo);
		PortujavaLexer lexer = new PortujavaLexer(cod);
		PortujavaParser parser = new PortujavaParser(new CommonTokenStream(
				lexer));
		Programa prog = parser.programa();
		prog.executar(escopo);
	}
	
	@Test
	public void comentarioDeLinha() throws RecognitionException {
		executar("//qqq\nx = 5 + 3 * 10;//aaaa\n");
		assertEquals(35.0, escopo.get("x"));
	}
	
	@Test
	public void comentarioDeBloco() throws RecognitionException {
		executar("/*aaa*\naaa */x = 5 + 3 * 10;/*sadfamfk*/");
		assertEquals(35.0, escopo.get("x"));
	}

}
