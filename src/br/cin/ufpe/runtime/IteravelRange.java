package br.cin.ufpe.runtime;

import java.util.Iterator;

public class IteravelRange implements Iterable<Object>, Iterator<Object> {

	private Long inicio;
	private Long fim;
	private Long passo;
	private Long atual;
	private boolean aumentando;
	private boolean temProx;

	public IteravelRange(Long inicio, Long fim, Long passo) {
		if (inicio == fim)
			throw new IllegalArgumentException(
					"Valores de início e fim devem ser diferentes");

		this.inicio = inicio;
		this.fim = fim;
		this.passo = passo;
		if (this.passo == null)
			this.passo = 1l;
		this.aumentando = inicio < fim;
		if (!this.aumentando)
			this.passo = this.passo * -1;
		this.atual = this.inicio;
		this.temProx = true;
	}

	@Override
	public Iterator<Object> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return temProx;
	}

	@Override
	public Object next() {
		if ((aumentando && atual >= fim) || (!aumentando && atual <= fim)) {
			atual = fim;
			temProx = false;
		}
		Long rv = atual;
		atual += passo;
		return rv;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
