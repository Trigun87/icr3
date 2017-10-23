package it.uniroma3.icr.model;

import java.util.Comparator;

public class ComparatorePerData implements Comparator<Task> {

	@Override
	public int compare(Task t1, Task t2) {
		return t1.getStartDate().compareTo(t2.getStartDate());
	}

}
