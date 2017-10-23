package it.uniroma3.icr.model;

import java.util.ArrayList;
import java.util.List;

public class TaskWrapper {

	private List<Result> resultList;
	
	public TaskWrapper() {
		resultList = new ArrayList<>();
	}

	public List<Result> getResultList() {
		return resultList;
	}

	public void setResultList(List<Result> resultList) {
		this.resultList = resultList;
	}
	
}
