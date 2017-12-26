package it.uniroma3.icr.dao.impl;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.icr.dao.ResultDaoCustom;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.model.TaskWrapper;

@Repository
@Transactional(readOnly=false)
public class ResultDaoImpl implements ResultDaoCustom {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void updateListResult(TaskWrapper taskResults) {
		for(Result r : taskResults.getResultList()) {
			LOGGER.info("UPDATE result: task " + r.getTask().getId() + " student " + r.getTask().getStudent().getId() + " result " +  r.getId());

			String update = "update result set answer = ?1 where id = ?2";
			Query query = this.entityManager.createNativeQuery(update).setParameter(1, r.getAnswer()).
					setParameter(2, r.getId());
			if (query.executeUpdate()!=1) {
				LOGGER.info("PROBLEM IN updateListResult - task " + r.getTask().getId() + " student " + r.getTask().getStudent().getId() + " result " +  r.getId());
			}				
//			this.entityManager.merge(r);
		}
	}
	@Override
	public void addImageAdnTaskToResult(Task t, Result r, Job j) {
		for(Image i : j.getImages()) {
			r = new Result();
			r.setImage(i);
			r.setTask(t);
		}
	}

}