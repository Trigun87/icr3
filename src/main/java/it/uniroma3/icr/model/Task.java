package it.uniroma3.icr.model;

import java.sql.*;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private int batch;
	
	@Column
	private java.sql.Timestamp startDate;
	
	@Column
	private java.sql.Timestamp endDate;
	
	@ManyToOne
	private Student student;
	
	@ManyToOne
	private StudentSocial studentsocial;
	
	@ManyToOne
	private Job job;
	
	@OneToMany(mappedBy="task")
	private List<Result> results;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	

	public StudentSocial getStudentsocial() {
		return studentsocial;
	}

	public void setStudentsocial(StudentSocial studentsocial) {
		this.studentsocial = studentsocial;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public Task(Long id, int batch, Timestamp startDate, Timestamp endDate, Student student,StudentSocial studentsocial, Job job,
			List<Result> results) {
		this.id = id;
		this.batch = batch;
		this.startDate = startDate;
		this.endDate = endDate;
		this.student = student;
		this.studentsocial=studentsocial;
		this.job = job;
		this.results = results;
	}

	public java.sql.Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(java.sql.Timestamp startDate) {
		this.startDate = startDate;
	}

	public java.sql.Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(java.sql.Timestamp endDate) {
		this.endDate = endDate;
	}

	public Task() {
		
	}
	
	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	@Override
	public String toString() {
		return "Task [id=" + id;
	}
	
}