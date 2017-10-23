package it.uniroma3.icr.model;

import java.util.Comparator;

public class ComparatoreResultPerWordeX implements Comparator<Result> {

	@Override
	public int compare(Result o1, Result o2) {
		int cmp=o1.getImage().getWord().hashCode()-o2.getImage().getWord().hashCode();
		if(cmp==0)
			cmp=o1.getImage().getX()-o2.getImage().getX();
		return cmp;
	}

}
