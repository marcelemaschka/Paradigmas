package br.cin.ufpe;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;

import br.cin.ufpe.ast.Programa;
import br.cin.ufpe.ast.Retornar.Retorno;
import br.cin.ufpe.runtime.Escopo;
import br.cin.ufpe.runtime.funcoes.Util;

public class TestComandos {

	private Escopo escopo;

	public void executar(String codigo) throws RecognitionException, Retorno {
		escopo = new Escopo();
		Util.embutirFuncoes(escopo);
		ANTLRStringStream cod = new ANTLRStringStream(codigo);
		PortujavaLexer lexer = new PortujavaLexer(cod);
		PortujavaParser parser = new PortujavaParser(new CommonTokenStream(
				lexer));
		Programa prog = parser.programa();
		prog.executar(escopo);
	}

	@Test
	public void atribuicaoSimples() throws RecognitionException, Retorno {
		executar("a = 2;");
		assertEquals(2L, escopo.get("a"));
	}

	@Test
	public void atribuicao() throws RecognitionException, Retorno {
		executar("x = 5 + 3 * 10;");
		assertEquals(35.0, escopo.get("x"));
	}

	@Test
	public void se() throws RecognitionException, Retorno {
		executar("x=10;y=0; se x<=10 { y=1000; }");
		assertEquals(1000L, escopo.get("y"));
	}

	@Test
	public void senao() throws RecognitionException, Retorno {
		executar("x=1;y=2; se falso { y=3; } senao { x = 4; }");
		assertEquals(2L, escopo.get("y"));
		assertEquals(4L, escopo.get("x"));
	}

	@Test
	public void senaoSe() throws RecognitionException, Retorno {
		executar("se 1>2 {x=1000;} senao se 2>1 {x=2000;} senao se 3>1 {x=2;} senao {x=10;}");
		assertEquals(2000l, escopo.get("x"));
	}

	@Test
	public void enquanto() throws RecognitionException, Retorno {
		executar("x=0; enquanto x<100 { x=x+1; }");
		assertEquals(100.0, escopo.get("x"));
	}

	@Test
	public void para() throws RecognitionException, Retorno {
		executar("fatorial=1;para x em :10 a 1: {fatorial=fatorial*x;}");
		assertEquals(3628800.0, escopo.get("fatorial"));
	}

	@Test
	public void escreva() throws RecognitionException, Retorno, IOException {
		PrintStream out = System.out;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));
		executar("escrevaln('O valor de x é '+10*5);");
		baos.flush();
		baos.close();
		String str = new String(baos.toByteArray());
		assertEquals(true, "O valor de x é 50.0\n".equals(str));
		System.setOut(out);
	}

}
