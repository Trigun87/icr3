package it.uniroma3.icr.view;

public class StudentsProductivity {
	
	private long id;
	
	private String name;
	
	private String surname;
	
	private int numeroTask;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getNumeroTask() {
		return numeroTask;
	}

	public void setNumeroTask(int numeroTask) {
		this.numeroTask = numeroTask;
	}
	
	public StudentsProductivity() {
		
	}

	public StudentsProductivity(long id, String name, String surname, int numeroTask) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.numeroTask = numeroTask;
	}
	

}
