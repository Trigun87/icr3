package it.uniroma3.icr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.ResultDao;
import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.model.TaskWrapper;

@Service
public class ResultFacade {

	@Autowired
	private ResultDao resultDao;
	
	public void addResult(Result r) {
		resultDao.save(r);
	}
	
	public Result retrieveResult(long id) {
		return this.resultDao.findOne(id);
	}
	
	public List<Result> retrieveAllResult() {
		return this.resultDao.findAll();
	}
	
	public List<Result> findTaskResult(Task task) {
		return this.resultDao.findByTask(task);
	}
	
	public void updateListResult(TaskWrapper taskResults) {
		resultDao.updateListResult(taskResults);
	}
	
	public void addImageAndTaskToResult(Task t,Result r, Job j) {
		resultDao.addImageAdnTaskToResult(t, r, j);
	}
	
}
