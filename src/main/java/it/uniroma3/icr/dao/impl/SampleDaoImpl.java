package it.uniroma3.icr.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.icr.dao.SampleDaoCustom;
import it.uniroma3.icr.model.Sample;

@Repository
@Transactional(readOnly=false)
public class SampleDaoImpl implements SampleDaoCustom {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@SuppressWarnings("unchecked")
	public List<Sample> findAllSamplesBySymbolId(long id) {
		String s = "FROM Sample s Where s.symbol.id = :id";
		Query query = this.entityManager.createQuery(s);
		query.setParameter("id", id);
		List<Sample> samples = query.getResultList();
		return samples;
	}

}
