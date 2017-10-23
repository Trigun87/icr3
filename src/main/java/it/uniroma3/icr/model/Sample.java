package it.uniroma3.icr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Sample {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable=false)
	private int x;

	@Column(nullable=false)
	private int y;

	@Column(nullable=false)
	private int width;

	@Column(nullable=false)
	private int height;
	
	@Column(nullable=false)
	private String type;

	@Column(nullable=false)
	private String path;
	
	@ManyToOne
	private Manuscript manuscript;
	
	@ManyToOne
	private Symbol symbol;
	// costruttori
	public Sample() {}

	public Sample(int width,int height,int xImg, int yImg,
			Manuscript manuscript,String type,String path) {
		this.width = width;
		this.height = height;
		this.x = xImg;
		this.y = yImg;
		this.manuscript = manuscript;
		this.type = type;
		this.path = path;
	}

	// getter e setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}


	public Manuscript getManuscript() {
		return manuscript;
	}

	public void setManuscript(Manuscript manuscript) {
		this.manuscript = manuscript;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}
}
