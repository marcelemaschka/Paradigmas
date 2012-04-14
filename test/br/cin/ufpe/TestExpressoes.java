package br.cin.ufpe;

import static org.junit.Assert.*;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;

import br.cin.ufpe.ast.Expressao;

public class TestExpressoes {

	private PortujavaLexer lexer;
	private PortujavaParser parser;

	public Object parse(String codigo) throws RecognitionException {
		ANTLRStringStream cod = new ANTLRStringStream(codigo);
		lexer = new PortujavaLexer(cod);
		parser = new PortujavaParser(new CommonTokenStream(lexer));
		Expressao exp = parser.programa();
		return exp.valor();
	}
	
	@Test
	public void numeroNegativo() throws RecognitionException {
		assertEquals(-5.0,  parse("-5"));
	}
	
	@Test
	public void numeroNegativoExprBinaria() throws RecognitionException {
		assertEquals(-10.0,  parse("5 * -2"));
	}

	@Test
	public void soma() throws RecognitionException {
		assertEquals(38.0,  parse("2+2+5*3*2+4"));
	}
	
	@Test
	public void multiplicacao() throws RecognitionException {
		assertEquals(21.0, parse("4.2*5"));
	}
	
	@Test
	public void expressaoComParentesis() throws RecognitionException {
		assertEquals(56.0, parse("8*(2+5)"));
	}
			

}
