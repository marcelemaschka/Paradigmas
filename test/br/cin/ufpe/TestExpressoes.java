package br.cin.ufpe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;

import br.cin.ufpe.ast.Expressao;
import br.cin.ufpe.ast.Programa;
import br.cin.ufpe.ast.Retornar.Retorno;
import br.cin.ufpe.runtime.Escopo;
import br.cin.ufpe.runtime.Objeto;

public class TestExpressoes {

	private Escopo escopo;
	private PortujavaParser parser;

	public Object calcular(String codigo) throws RecognitionException {
		escopo = new Escopo();
		ANTLRStringStream cod = new ANTLRStringStream(codigo);
		PortujavaLexer lexer = new PortujavaLexer(cod);
		parser = new PortujavaParser(new CommonTokenStream(lexer));
		Expressao exp = parser.expressao();
		if (parser.getNumberOfSyntaxErrors() > 0) {
			return null;
		}
		return exp.valor(escopo);
	}

	public void executar(String codigo) throws RecognitionException, Retorno {
		escopo = new Escopo();
		ANTLRStringStream cod = new ANTLRStringStream(codigo);
		PortujavaLexer lexer = new PortujavaLexer(cod);
		parser = new PortujavaParser(new CommonTokenStream(lexer));
		Programa prog = parser.programa();
		if (parser.getNumberOfSyntaxErrors() > 0) {
			return;
		}
		prog.executar(escopo);
	}

	@Test
	public void numeroNegativo() throws RecognitionException {
		assertEquals(-5l, calcular("-5"));
		assertEquals(5l, calcular("-(-5)"));
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
	public void comparacaoBooleanos() throws RecognitionException {
		assertEquals(true, calcular("verdadeiro==verdadeiro"));
		assertEquals(true, calcular("falso==falso"));
		assertEquals(true, calcular("verdadeiro!=falso"));
		assertEquals(true, calcular("falso!=verdadeiro"));
		assertEquals(false, calcular("verdadeiro!=verdadeiro"));
		assertEquals(false, calcular("falso!=falso"));
		assertEquals(false, calcular("verdadeiro==falso"));
		assertEquals(false, calcular("falso==verdadeiro"));
	}

	@Test
	public void comparacaoStrings() throws RecognitionException {
		assertEquals(true, calcular("'texto teste'=='texto teste'"));
		assertEquals(false, calcular("'texto teste 1'=='texto teste'"));
	}

	@Test
	public void comparacaoNumeros() throws RecognitionException {

		assertEquals(false, calcular("5>=6"));
		assertEquals(true, calcular("5>=5"));
		assertEquals(true, calcular("5>=4"));

		assertEquals(false, calcular("3>4"));
		assertEquals(false, calcular("3>3"));
		assertEquals(true, calcular("3>2"));

		assertEquals(true, calcular("5<6"));
		assertEquals(false, calcular("5<5"));
		assertEquals(false, calcular("5<4"));

		assertEquals(true, calcular("5<=6"));
		assertEquals(true, calcular("5<=5"));
		assertEquals(false, calcular("5<=4"));

		assertEquals(true, calcular("5.4 < 6"));

		assertEquals(true, calcular("25==25"));
		assertEquals(false, calcular("25==26"));
		assertEquals(true, calcular("25==25.0"));
		assertEquals(true, calcular("25.0==25"));

		assertEquals(true, calcular("25!=26"));
		assertEquals(false, calcular("25!=25"));
		assertEquals(false, calcular("25!=25.0"));
		assertEquals(false, calcular("25.0!=25"));

	}

	@Test
	public void comparacaoMixed() throws RecognitionException {
		assertEquals(false, calcular("'1'== 1"));
		assertEquals(true, calcular("(((1+1)==3)==falso)"));
		assertEquals(true, calcular("1+1==3==falso"));
		assertEquals(true, calcular("falso==verdadeiro==(1-2==3))"));
		assertEquals(true, calcular("falso==falso==verdadeiro==falso==falso"));
		assertEquals(false, calcular("1<2==(3>4)==falso==(12<=3*4)==falso"));

	}

	@Test
	public void comparacaoMalFormada() throws RecognitionException, Retorno {
		try {
			executar("x=0;se 3>2>1 {x=1;}");
			fail("Esperando erro de sintaxe");
		} catch (RuntimeException e) {
			assertTrue(e.getCause() instanceof MismatchedTokenException);
		}
	}

	@Test
	public void comparacaoECondicional() throws RecognitionException, Retorno {
		executar("x=0;se 0==x==verdadeiro {x=1;}");
		assertEquals(1L, escopo.get("x"));
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
		assertEquals(-10l, calcular("5 *    -2"));
	}

	@Test
	public void expressaoUnaria() throws RecognitionException {
		assertEquals(false, calcular("5>1<<1*3"));
		assertEquals(16l, calcular("128>>6/2"));
	}

	@Test
	public void soma() throws RecognitionException {
		assertEquals(38l, calcular("2+2+5*3*2+4"));
	}

	@Test
	public void multiplicacao() throws RecognitionException {
		assertEquals(21.0, calcular("4.2*5"));
	}

	public void multiplicacaoString() throws RecognitionException {
		assertEquals("testeteste", calcular("'teste'*2"));
	}

	@Test
	public void expressaoComParentesis() throws RecognitionException {
		assertEquals(56l, calcular("8*(2+    5)"));
	}

	@Test
	public void lista() throws RecognitionException {
		@SuppressWarnings("unchecked")
		List<Object> val = (List<Object>) calcular("[7,3,13,'abc']");
		assertEquals(4, val.size());
		assertEquals(7L, val.get(0));
		assertEquals(3L, val.get(1));
		assertEquals(13L, val.get(2));
		assertEquals("abc", val.get(3));
	}

	@Test
	public void listaConstruidaComRange() throws RecognitionException {
		@SuppressWarnings("unchecked")
		List<Object> val = (List<Object>) calcular("[1 a 4]");
		assertEquals(4, val.size());
		assertEquals(1L, val.get(0));
		assertEquals(2L, val.get(1));
		assertEquals(3L, val.get(2));
		assertEquals(4L, val.get(3));
	}

	@Test
	public void listaConstruidaComRangeDescrescenteEPasso()
			throws RecognitionException {
		@SuppressWarnings("unchecked")
		List<Object> val = (List<Object>) calcular("[10 a 1, 2]");
		assertEquals(6, val.size());
		assertEquals(10L, val.get(0));
		assertEquals(8L, val.get(1));
		assertEquals(6L, val.get(2));
		assertEquals(4L, val.get(3));
		assertEquals(2L, val.get(4));
		assertEquals(1L, val.get(5));
	}

	@Test
	public void objeto() throws RecognitionException {
		Objeto val = (Objeto) calcular("{nome: 'Foo', 'tipo': 'Bar'}");
		assertEquals(val.get("nome"), "Foo");
		assertEquals(val.get("tipo"), "Bar");
	}

	@Test
	public void acessoAttributo() throws RecognitionException {
		assertEquals("Foo", calcular("{nome: 'Foo', 'tipo': 'Bar'}.nome"));
	}

	@Test
	public void acessoAttributoAninhado() throws RecognitionException {
		assertEquals("232",
				calcular("{nome: 'Foo', endereco:{contato:{fone: '232'}}}."
						+ "endereco['contato'].fone"));
	}
}
