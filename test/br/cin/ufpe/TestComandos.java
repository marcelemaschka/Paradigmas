package br.cin.ufpe;

import static org.junit.Assert.assertEquals;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;

import br.cin.ufpe.ast.Bloco;
import br.cin.ufpe.ast.Escopo;

public class TestComandos {

	private Escopo escopo;

	public void executar(String codigo) throws RecognitionException {
		escopo = new Escopo();
		ANTLRStringStream cod = new ANTLRStringStream(codigo);
		PortujavaLexer lexer = new PortujavaLexer(cod);
		PortujavaParser parser = new PortujavaParser(new CommonTokenStream(
				lexer));
		Bloco bloco = parser.bloco();
		bloco.executar(escopo);
	}

	@Test
	public void atribuicao() throws RecognitionException {
		executar("x = 5 + 3 * 10;");
		assertEquals(35.0, escopo.get("x"));
	}

}
