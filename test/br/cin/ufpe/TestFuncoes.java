package br.cin.ufpe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Before;
import org.junit.Test;

import br.cin.ufpe.ast.Programa;
import br.cin.ufpe.ast.Retornar;
import br.cin.ufpe.ast.Retornar.Retorno;
import br.cin.ufpe.runtime.Escopo;

public class TestFuncoes {

	private Escopo escopo;

	@Before
	public void setup() {
		escopo = new Escopo();
	}
	
	public void executar(String codigo) throws RecognitionException, Retorno {
		ANTLRStringStream cod = new ANTLRStringStream(codigo);
		PortujavaLexer lexer = new PortujavaLexer(cod);
		PortujavaParser parser = new PortujavaParser(new CommonTokenStream(
				lexer));
		Programa prog = parser.programa();
		prog.executar(escopo);
	}

	@Test
	public void retorno() throws RecognitionException {
		try {
			executar("retornar 5;");
			fail("Nao retornou nada");
		} catch (Retorno e) {
			assertEquals(5L, e.getValor());
		}
	}

	@Test
	public void declaracaoEChamada() throws RecognitionException, Retorno {
		executar("funcao f(x) { retornar x + 5; } y = f(10);");
		assertEquals(15l, escopo.get("y"));		
	}
	

	@Test
	public void funcaoRecursiva() throws RecognitionException, Retorno {
		executar("funcao fatorial(n) { se n == 1 { retornar 1; } retornar n * fatorial(n-1); } y = fatorial(5);");
		assertEquals(120l, escopo.get("y"));		
	}
	
	@Test
	public void closures() throws RecognitionException, Retorno {
		executar("funcao f() { i=0;funcao closure() { @i=@i+1; retornar @i; } "
				+ "retornar closure; } g1 = f(); g2 = f();");
		executar("x = g1();");
		assertEquals(1l, escopo.get("x"));
		executar("x = g1();x = g1();x = g1();");
		assertEquals(4l, escopo.get("x"));
		executar("x = g2();x = g2();");
		assertEquals(2l, escopo.get("x"));
		executar("x = g1();");
		assertEquals(5l, escopo.get("x"));
	}
	
	
	
}
