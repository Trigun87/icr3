package it.uniroma3.icr.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.icr.dao.WordDaoCustom;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Word;

@Repository
@Transactional(readOnly=false)
public class WordDaoImpl implements WordDaoCustom{

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Word> findWordForManuscriptName(String manuscript, int limit) {
		String s = "FROM Word w WHERE w.manuscript.name = :manuscript ORDER BY RANDOM()";
		Query query = this.entityManager.createQuery(s);
		query.setParameter("manuscript", manuscript);
		List<Word> words = query.setMaxResults(limit).getResultList();
		
		
		return words;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAllManuscript() {
		String s = "SELECT distinct manuscript FROM Word";
		Query query = this.entityManager.createQuery(s);
		List<String> manuscripts = query.getResultList();
		return manuscripts;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findAllPages() {
		String s = "SELECT distinct page FROM Word";
		Query query = this.entityManager.createQuery(s);
		List<String> pages = query.getResultList();
		return pages;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] countWord() {
		String s = "select count (*), width from word group by width";
		Query query = this.entityManager.createQuery(s);
		List<Image> images = query.getResultList();
		Object[] objectList = images.toArray();
		return objectList;
	}

}
