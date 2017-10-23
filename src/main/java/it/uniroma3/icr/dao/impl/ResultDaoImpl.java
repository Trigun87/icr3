package it.uniroma3.icr.dao.impl;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void updateListResult(TaskWrapper taskResults) {
		for(Result r : taskResults.getResultList())
			this.entityManager.merge(r);
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