package br.cin.ufpe.ast;

import br.cin.ufpe.runtime.Acesso;
import br.cin.ufpe.runtime.Escopo;

public class Atribuicao extends Comando {

	private Expressao alvo;
	private Expressao expressao;

	public Atribuicao(Expressao alvo, Expressao expressao) {
		if (!(alvo instanceof AcessoAtributo || alvo instanceof Identificador))
			throw new RuntimeException(
					"Lado esquerdo de atribuiçao deve ser um identificador ou acesso a atributo");
		this.alvo = alvo;
		this.expressao = expressao;
	}

	@Override
	public void executarCmd(Escopo escopo) {
		Object valor = expressao.valor(escopo);
		if (alvo instanceof Identificador) {
			int nivel = ((Identificador)alvo).getNivel();
			Escopo e = escopo;
			for (int i = 0; i < nivel && e.getSuperEscopo() != null; i++)
				e = e.getSuperEscopo();
			e.put(((Identificador) alvo).getNome(), valor);
		}
		else {
			AcessoAtributo acesso = (AcessoAtributo) alvo;
			Object tgt = acesso.getAlvo().valor(escopo);
			Acesso.chamarMetodo(Acesso.SETTER, tgt,
					acesso.getArg().valor(escopo), valor);
		}
	}
}
