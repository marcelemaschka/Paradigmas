package br.cin.ufpe.runtime;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Lista extends ArrayList<Object> {

	public Lista(Iterable<Object> iteravel) {
		for (Object object : iteravel)
			add(object);
	}
}
