package it.uniroma3.icr.model;


import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToOne;


@Entity 
public class Result {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Image image;

	@ManyToOne
	private Task task;

	@Column
	private String answer;
	
	public Result() {}
	
	public Result(Long id, Image image, Task task, String answer) {
		super();
		this.id = id;
		this.image = image;
		this.task = task;
		this.answer = answer;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Result [id=" + id + ", "
				+ "image=" + image + ", "
				+ "task=" + task + ", "
				+ "answer=" + answer + "]";
	}

}
