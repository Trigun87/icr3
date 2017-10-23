package it.uniroma3.icr.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Symbol {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String transcription;
	
	@Column(nullable=false)
	private String type;
	
	@Column(nullable =false)
	private int width;
	
	@ManyToOne
	private Manuscript manuscript;
	
	@OneToMany(mappedBy="symbol")
	private List<Sample> samples;
	
	@OneToMany(mappedBy="symbol")
	private List<Job> jobs;
	
	// costruttori
	public Symbol() {}
	
	public Symbol (String transcription, String type, Manuscript manuscript, int width) {
		this.transcription = transcription;
		this.type = type;
		this.width = width;
		this.manuscript = manuscript;
	}

	public Symbol(Long id, String transcription, String type, List<Job> jobs) {
		this.id = id;
		this.transcription = transcription;
		this.type = type;
		this.jobs = jobs;
	}
	// getter e setter
	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Manuscript getManuscript() {
		return manuscript;
	}

	public void setManuscript(Manuscript manuscript) {
		this.manuscript = manuscript;
	}

	public String getTranscription() {
		return transcription;
	}

	public void setTranscription(String transcription) {
		this.transcription = transcription;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Symbol [id=" + id + ", transcription=" + transcription + ", type=" + type +  "]";
	}

	public List<Sample> getSamples() {
		return samples;
	}

	public void setSamples(List<Sample> samples) {
		this.samples = samples;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
}
