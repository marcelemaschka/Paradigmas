package br.cin.ufpe;

import static org.junit.Assert.assertEquals;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;

import br.cin.ufpe.ast.Escopo;
import br.cin.ufpe.ast.Programa;
import br.cin.ufpe.ast.Retornar.Retorno;

public class TestComandos {

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
	public void atribuicao() throws RecognitionException, Retorno {
		executar("x = 5 + 3 * 10;");
		assertEquals(35.0, escopo.get("x"));
	}

	@Test
	public void se() throws RecognitionException, Retorno {
		executar("x=10;y=0; se (x<=10) { y=1000; }");
		assertEquals(1000L, escopo.get("y"));
	}
	
	@Test
	public void senao() throws RecognitionException, Retorno {
		executar("x=1;y=2; se (falso) { y=3; } senao { x = 4; }");
		assertEquals(2L, escopo.get("y"));
		assertEquals(4L, escopo.get("x"));
		
	}

	@Test
	public void enquanto() throws RecognitionException, Retorno {
		executar("x=0; enquanto (x<100) { x=x+1; }");
		assertEquals(100.0, escopo.get("x"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void escopo() throws RecognitionException, Retorno{
		executar("x=0;se(x==0){y=2;}x=y+2;");
		assertEquals(false, (Double)(escopo.get("x"))==4);
	}
	
	@Test
	public void para() throws RecognitionException, Retorno{
		
		executar("fatorial=1;para(x=10;x>0;x=x-1;){fatorial=fatorial*x;}");
		assertEquals(3628800.0, escopo.get("fatorial"));
	}

}
