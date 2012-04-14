package br.cin.ufpe;

import static org.junit.Assert.*;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;

import br.cin.ufpe.ast.Escopo;
import br.cin.ufpe.ast.Expressao;

public class TestExpressoes {

	private Escopo escopo;

	public Object calcular(String codigo) throws RecognitionException {
		escopo = new Escopo();
		ANTLRStringStream cod = new ANTLRStringStream(codigo);
		PortujavaLexer lexer = new PortujavaLexer(cod);
		PortujavaParser parser = new PortujavaParser(new CommonTokenStream(
				lexer));
		Expressao exp = parser.expressao();
		return exp.valor(escopo);
	}

	@Test
	public void numeroNegativo() throws RecognitionException {
		assertEquals(-5.0, calcular("-5"));
	}

	@Test
	public void valoresBooleanos() throws RecognitionException {
		assertEquals(true, calcular("verdadeiro"));
		assertEquals(false, calcular("falso"));
	}

	@Test
	public void valorNot() throws RecognitionException {
		assertEquals(false, calcular("!verdadeiro"));
		assertEquals(true, calcular("!falso"));
	}

	@Test
	public void stringLiteral() throws RecognitionException {
		assertEquals("Testando ' \n 123", calcular("'Testando \\' \\n 123'"));
	}

	@Test
	public void somaStrings() throws RecognitionException {
		assertEquals("Teste 1  Teste2", calcular("'Teste 1'  +  '  Teste2'"));
	}

	@Test
	public void somaStringOutrosTiposEsquerda() throws RecognitionException {
		assertEquals("Teste 1", calcular("'Teste ' + 1"));
	}

	@Test
	public void somaStringOutrosTiposDireita() throws RecognitionException {
		assertEquals("verdadeiroTeste", calcular("verdadeiro + 'Teste'"));
	}

	@Test
	public void comparacoes() throws RecognitionException {
		assertEquals(true, calcular("verdadeiro==verdadeiro"));
		assertEquals(true, calcular("falso==falso"));
		assertEquals(true, calcular("verdadeiro!=falso"));
		assertEquals(true, calcular("falso!=verdadeiro"));
		assertEquals(false, calcular("verdadeiro!=verdadeiro"));
		assertEquals(false, calcular("falso!=falso"));
		assertEquals(false, calcular("verdadeiro==falso"));
		assertEquals(false, calcular("falso==verdadeiro"));
		assertEquals(true, calcular(" 5>=4"));
		assertEquals(true, calcular(" 5>=4"));
		assertEquals(false, calcular(" 3>4"));
		assertEquals(true, calcular(" 5.4 < 6"));
		assertEquals(false, calcular(" 10<=9"));
		assertEquals(true, calcular(" 25==25.0"));
		assertEquals(true, calcular("1!=0"));
		assertEquals(true, calcular("'texto teste'=='texto teste'"));
		assertEquals(false, calcular("'texto teste 1'=='texto teste'"));
		assertEquals(false, calcular("'1'== 1"));
		assertEquals(true, calcular("1+1==3==falso"));
	}

	@Test
	public void conjuncao() throws RecognitionException {
		assertEquals(false, calcular(" 5>=4 && 4>10"));
	}

	@Test
	public void disjuncao() throws RecognitionException {
		assertEquals(true, calcular("5>=4 && 4>10 || verdadeiro "));
	}

	@Test
	public void numeroNegativoExprBinaria() throws RecognitionException {
		assertEquals(-10.0, calcular("5 *    -2"));
	}

	@Test
	public void soma() throws RecognitionException {
		assertEquals(38.0, calcular("2+2+5*3*2+4"));
	}

	@Test
	public void multiplicacao() throws RecognitionException {
		assertEquals(21.0, calcular("4.2*5"));
	}

	@Test
	public void expressaoComParentesis() throws RecognitionException {
		assertEquals(56.0, calcular("8*(2+    5)"));
	}

}
