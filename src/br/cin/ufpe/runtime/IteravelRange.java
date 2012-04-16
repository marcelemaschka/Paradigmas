package br.cin.ufpe.runtime;

import java.util.Iterator;

public class IteravelRange implements Iterable<Object> {

	private Long inicio;
	private Long fim;
	private Long passo;

	public IteravelRange(Long inicio, Long fim, Long passo) {
		this.inicio = inicio;
		this.fim = fim;
		this.passo = passo;
	}

	@Override
	public Iterator<Object> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	private static class IteradorRange implements Iterator<Object> {

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object next() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

	}

}
