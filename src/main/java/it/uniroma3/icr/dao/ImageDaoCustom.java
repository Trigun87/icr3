package it.uniroma3.icr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.uniroma3.icr.model.Image;

@Repository
public interface ImageDaoCustom {
	public List<Image> findImageForTypeAndManuscriptName(String type, String manuscript, int limit);

	public List<Image> findImageForTypeAndWidthAndManuscript(String type, String manuscript, int width, int limit);

	public List<String> findAllManuscript();

	public List<String> findAllPages();

	public Object[] countImage();

	public List<Image> findImageFromManuscriptName(long manuscript);

}
