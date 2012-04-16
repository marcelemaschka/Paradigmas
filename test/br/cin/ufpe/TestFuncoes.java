package br.cin.ufpe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;

import br.cin.ufpe.ast.Programa;
import br.cin.ufpe.ast.Retornar.Retorno;
import br.cin.ufpe.runtime.Escopo;

public class TestFuncoes {

	private Escopo escopo;

	public void executar(String codigo) throws RecognitionException, Retorno {
		escopo = new Escopo();
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
		assertEquals(15.0, escopo.get("y"));		
	}
	

	@Test
	public void funcaoRecursiva() throws RecognitionException, Retorno {
		executar("funcao fatorial(n) { se(n == 1) { retornar 1; } retornar n * fatorial(n-1); } y = fatorial(5);");
		assertEquals(120.0, escopo.get("y"));		
	}
}
