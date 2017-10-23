package it.uniroma3.icr.model;

import java.util.Comparator;

public class ComparatoreSimboloPerNome implements Comparator<Symbol> {

	@Override
	public int compare(Symbol s1, Symbol s2) {
		return s1.getTranscription().compareTo(s2.getTranscription());
	}

}
