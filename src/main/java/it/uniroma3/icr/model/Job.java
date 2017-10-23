package it.uniroma3.icr.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Job {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String title;
	private String description;
	@Column(nullable=false)
	private Integer taskSize;
	private Integer numberOfImages;
	private Integer numberOfWords;
	@Column(nullable=false)
	private Integer numberOfStudents;
	private String difficulty;
	@ManyToOne
	private Manuscript manuscript;
	@ManyToMany
	private List<Image> images;
	@ManyToMany
	private List<Word> words;
	@OneToMany(mappedBy="job")
	private List<Task> tasks; 
	@ManyToOne
	private Symbol symbol;
	
	public Job() {
		this.tasks = new ArrayList<>();
	}
	
	public Job(Long id, String title, Integer taskSize, 
			Integer numberOfImages, Integer numberOfStudents, 
			List<Image> images, List<Task> tasks, 
			Symbol symbol) {
		this.id = id;
		this.title = title;
		this.taskSize = taskSize;
		this.numberOfImages = numberOfImages;
		this.numberOfStudents = numberOfStudents;
		this.images = images;
		this.tasks = tasks;
		this.symbol = symbol;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	public Integer getNumberOfImages() {
		return numberOfImages;
	}

	public void setNumberOfImages(Integer numberOfImages) {
		this.numberOfImages = numberOfImages;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public Integer getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(Integer numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	public Integer getStudents() {
		return numberOfStudents;
	}

	public void setStudents(Integer students) {
		this.numberOfStudents = students;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTaskSize() {
		return taskSize;
	}

	public void setTaskSize(Integer taskSize) {
		this.taskSize = taskSize;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Manuscript getManuscript() {
		return manuscript;
	}
	
	public void setManuscript(Manuscript manuscript) {
		this.manuscript = manuscript;
	}

	public Integer getNumberOfWords() {
		return numberOfWords;
	}

	public void setNumberOfWords(Integer numberOfWords) {
		this.numberOfWords = numberOfWords;
	}

	public List<Word> getWords() {
		return words;
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}
	
	public void addTask(Task task){
		this.tasks.add(task);
	}
	

	

}
