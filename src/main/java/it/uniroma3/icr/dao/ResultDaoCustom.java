package it.uniroma3.icr.dao;

import org.springframework.stereotype.Repository;

import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Task;
import it.uniroma3.icr.model.TaskWrapper;
@Repository
public interface ResultDaoCustom {
	public void addImageAdnTaskToResult(Task t,Result r,Job j);
	public void updateListResult(TaskWrapper taskResults);
}
