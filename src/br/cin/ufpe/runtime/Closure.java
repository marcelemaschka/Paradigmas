package br.cin.ufpe.runtime;

import java.util.List;

import br.cin.ufpe.ast.Bloco;
import br.cin.ufpe.ast.Escopo;
import br.cin.ufpe.ast.Retornar.Retorno;

public class Closure {
	private Escopo superEscopo;
	private List<String> parametros;
	private Bloco bloco;

	public Closure(Escopo superEscopo, List<String> parametros, Bloco bloco) {
		this.superEscopo = superEscopo;
		this.parametros = parametros;
		this.bloco = bloco;
	}

	public Object chamar(List<Object> args) {
		int len = args.size();
		if (len != parametros.size())
			throw new IllegalArgumentException(
					"Fun��o chamada com n�mero de argumentos diferente da declara��o.");
		// o primeiro passo � atribuir os valores dos argumentos
		// a seus respectivos identificadores
		Escopo escopo = new Escopo(superEscopo);
		for (int i = 0; i < len; i++)
			escopo.put(parametros.get(i), args.get(i));
		Object valorRetorno = null;
		try {
			bloco.executar(escopo);
		} catch (Retorno e) {
			valorRetorno = e.getValor();
		}
		return valorRetorno;
	}


	@Override
	public String toString() {
		return "<<Função>>";
	}
}