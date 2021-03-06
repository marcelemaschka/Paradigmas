package br.cin.ufpe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Before;
import org.junit.Test;

import br.cin.ufpe.ast.Programa;
import br.cin.ufpe.ast.Retornar.Retorno;
import br.cin.ufpe.runtime.Acesso;
import br.cin.ufpe.runtime.Escopo;
import br.cin.ufpe.runtime.funcoes.Util;

public class TestComandos {

	private Escopo escopo;
	private ByteArrayOutputStream saida;

	@Before
	public void setup() {
		saida = new ByteArrayOutputStream();
		escopo = new Escopo();
		Util.embutirClasses(escopo);
		Util.embutirFuncoes(escopo, null, saida, saida);
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
	public void atribuicaoSimples() throws RecognitionException, Retorno {
		executar("a = 2;");
		assertEquals(2L, escopo.get("a"));
	}

	@Test
	public void atribuicao() throws RecognitionException, Retorno {
		executar("x = 5 + 3 * 10;");
		assertEquals(35l, escopo.get("x"));
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
		assertEquals(100l, escopo.get("x"));
	}

	@Test
	public void para() throws RecognitionException, Retorno {
		executar("fatorial=1;para x em :10 a 1: {fatorial=fatorial*x;}");
		assertEquals(3628800l, escopo.get("fatorial"));
	}

	@Test
	public void paraIntervaloComVariaveis() throws RecognitionException,
			Retorno {
		executar("inicio=3;fim=-2;pulo=2;soma=0;para x em :inicio a fim,pulo: {soma=soma+x;}");
		assertEquals(1l, escopo.get("soma"));
	}

	@Test
	public void escreva() throws RecognitionException, Retorno, IOException {
		executar("escrevaln('O valor de x � '+10*5);");
		saida.flush();
		String str = new String(saida.toByteArray());
		assertEquals("O valor de x � 50\n", str);
	}

	@Test
	public void sobrescritaDeOperador() throws RecognitionException, Retorno {
		executar("foo={nome: 'Foo', '#+#': funcao(operando) {retornar 1000;}}; x = foo+'sds';");
		assertEquals(1000l, escopo.get("x"));
	}

	@Test
	public void atribuicaoAtributo() throws RecognitionException, Retorno {
		executar("foo={nome: 'Foo', endereco:{Rua: 'X'}}; foo.nome = 'Bar';x=foo.nome;"
				+ "rua=foo.endereco.Rua;");
		assertEquals("Bar", escopo.get("x"));
		assertEquals("X", escopo.get("rua"));
		executar("foo['endereco']['Rua'] = 'Y';rua=foo.endereco.Rua;");
		assertEquals("Y", escopo.get("rua"));
	}

	@Test
	public void acessoSuperEscopos() throws RecognitionException, Retorno {
		executar("funcao outer() { funcao inner() { @@y=10; } @x=5; "
				+ "retornar inner; } i = outer();");
		assertEquals(5l, escopo.get("x"));
		assertNull(escopo.get("y"));
		executar("i();");
		assertEquals(10l, escopo.get("y"));
	}

	@Test
	public void metodo() throws RecognitionException, Retorno {
		executar("conta = {saldo:0, creditar: funcao(qtd){isto.saldo = isto.saldo + qtd;},"
				+ "debitar: funcao(qtd){isto.saldo = isto.saldo - qtd;}};s = conta.saldo;");
		assertEquals(0l, escopo.get("s"));
		executar("conta.creditar(10.5);s=conta.saldo;");
		assertEquals(10.5, escopo.get("s"));
		executar("conta.debitar(2.5);s=conta.saldo;");
		assertEquals(8.0, escopo.get("s"));
	}

	@Test
	public void construcaoDeInstancias() throws RecognitionException, Retorno {
		String codigo = "classe Conta (Objeto) {\n'" + Acesso.INIT
				+ "':funcao(saldo){isto.saldo= saldo;},\n"
				+ "creditar: funcao(qtd){isto.saldo = isto.saldo + qtd;},\n"
				+ "debitar: funcao(qtd){isto.saldo = isto.saldo - qtd;}\n}"
				+ "c = nova Conta(5);s=c.saldo;";
		executar(codigo);
		assertEquals(5l, escopo.get("s"));
		executar("c.creditar(10.5);s=c.saldo;");
		assertEquals(15.5, escopo.get("s"));
		executar("c.debitar(2.5);s=c.saldo;");
		assertEquals(13.0, escopo.get("s"));
	}
	
	@Test
	public void sobrescritaDeOperadores() throws RecognitionException, Retorno{
		executar("classe Vetor (Objeto){\r\n" + 
				"	'[init]': funcao(x, y, z) {\r\n" + 
				"		isto.x = x;\r\n" + 
				"		isto.y = y;\r\n" + 
				"		isto.z = z;\r\n" + 
				"	}, \r\n" + 
				"	'#+#': funcao(v2) {\r\n" + 
				"		rv = novo Vetor(0, 0, 0);\r\n" + 
				"		rv.x = isto.x + v2.x;\r\n" + 
				"		rv.y = isto.y + v2.y;\r\n" + 
				"		rv.z = isto.z + v2.z;\r\n" + 
				"		retornar rv;\r\n" + 
				"	}\r\n" + 
				"}");
		executar("v1 = novo Vetor(1,4,1);v2 = novo Vetor(2,2,2);");
		executar("v3 = v1 + v2;");		
		Map v3 = (Map) escopo.get("v3");
		assertEquals(3l, v3.get("x"));
		assertEquals(6l, v3.get("y"));
		assertEquals(3l, v3.get("z"));
		executar("v3 = v1 - v2;");
		
	
	}
	
}
