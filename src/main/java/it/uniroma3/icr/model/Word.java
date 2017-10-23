package it.uniroma3.icr.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Word {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@Column(nullable=false)
	private Integer x; 
	/*@Column(nullable=false)
	private Integer y;*/
	@Column(nullable=false)
	private Integer width;
	@Column(nullable=false)
	private Integer height;
	@Column(nullable=false)
	private String page;
	@Column(nullable=false)
	private String row;
	@ManyToOne
	private Manuscript manuscript;
	@ManyToMany(mappedBy = "words")
	private List<Job> jobs;
	@OneToMany(mappedBy = "word", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	private List<Image> images;
	
	public Word(){
		images = new ArrayList<>();
		jobs = new ArrayList<>();
	}

	public Word(String name, Integer x, Integer width, Integer height, Manuscript manuscript, String page,
			String row, List<Job> jobs, List<Image> images) {
		this.name = name;
		this.x = x;
		//this.y = y;
		this.width = width;
		this.height = height;
		this.manuscript = manuscript;
		this.page = page;
		this.row = row;
		this.jobs = jobs;
		this.images = images;
	}

	//Calcola la larghezza della parola data la lista di immagini
	public void findWidth(){
		int totalWidth=0;     
		for(int i = 0; i<this.images.size(); i++){
			totalWidth+=this.images.get(i).getWidth();
		}
		this.setWidth(totalWidth);
	}

	//	setter e getter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

/*	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}*/

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Manuscript getManuscript() {
		return manuscript;
	}

	public void setManuscript(Manuscript manuscript) {
		this.manuscript = manuscript;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void printImages(){
		for(int i = 0; i<this.images.size(); i++){
			System.out.println(this.images.get(i).toString());
		}
	}
	public void addImage(Image img){
		this.images.add(img);
	}
}
