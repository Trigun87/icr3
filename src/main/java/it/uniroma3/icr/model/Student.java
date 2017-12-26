package it.uniroma3.icr.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String surname;

	@Column(nullable=false)
	private String school;

	@Column(nullable = true)
	private String email;
	
	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String schoolGroup;

	@Column(nullable = false)
	private String section;

	@Column(nullable=false)
	private String role;
	
	@Column(nullable=false)
	private int tempoEffettuato = 0;

	@Column(nullable=false)
	private int taskEffettuati = 0;


	@OneToMany(mappedBy="student")
	private List<Task> tasks;

	public Student() {
		this.role = "ROLE_USER";
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getTempoEffettuato() {
		return tempoEffettuato;
	}

	public void setTempoEffettuato(int tempoEffettuato) {
		this.tempoEffettuato = tempoEffettuato;
	}

	public int getTaskEffettuati() {
		return taskEffettuati;
	}

	public void setTaskEffettuati(int taskEffettuati) {
		this.taskEffettuati = taskEffettuati;
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

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSchoolGroup() {
		return schoolGroup;
	}

	public void setSchoolGroup(String schoolGroup) {
		this.schoolGroup = schoolGroup;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void addTask(Task t) {
		this.tasks.add(t);
	}
	
	public boolean equals(Object object) {
		Student student = (Student)object;
		return this.id.equals(student.getId());
	}

	public int hashCode() {
		return this.id.hashCode();
	}


}