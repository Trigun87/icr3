package it.uniroma3.icr.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.icr.dao.ImageDaoCustom;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Word;

@Repository
@Transactional(readOnly=false)
public class ImageDaoImpl implements ImageDaoCustom {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Image> findImageForTypeAndManuscriptName(String type,String manuscript, int limit) {
		/*String s = "FROM Image i WHERE i.manuscript = :manuscript ORDER BY RANDOM()";
		Query query = this.entityManager.createQuery(s);
		query.setParameter("type", type);
		query.setParameter("manuscript", manuscript);
		List<Image> images = query.setMaxResults(limit).getResultList();
		
		
		return images;*/
		String s = "FROM Image i WHERE i.type = :type and i.manuscript = :manuscript ORDER BY RANDOM()";
		Query query = entityManager.createNativeQuery(s,Image.class).setMaxResults(limit);
		query.setParameter("type", type);
		query.setParameter("manuscript", manuscript);
		List<Image> images = query.getResultList();
		return images;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAllManuscript() {
		String s = "SELECT distinct manuscript FROM Image";
		return entityManager.createQuery(s).getResultList();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] countImage() {
		String s = "select count (*), type,width from image group by type,width";
		Query query = entityManager.createQuery(s);
		List<Image> images = query.getResultList();
		Object[] objectList = images.toArray();
		return objectList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAllPages() {
		String s = "SELECT distinct page FROM Image";
		Query query = entityManager.createQuery(s);
		List<String> pages = query.getResultList();
		return pages;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Image> findImageForTypeAndWidthAndManuscript(String type, String manuscript, int width, int limit) {
		String s = "FROM Image i WHERE i.type = :type and i.width = :width and i.manuscript = :manuscript ORDER BY RANDOM()";
		
		Query query = entityManager.createQuery(s);
		query.setParameter("type", type);
		query.setParameter("width", width);
		query.setParameter("manuscript", manuscript);
		
		List<Image> images = query.setMaxResults(limit).getResultList();
		return images;	}

}










