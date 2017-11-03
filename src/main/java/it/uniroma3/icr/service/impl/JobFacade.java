package it.uniroma3.icr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.JobDao;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Manuscript;
import it.uniroma3.icr.model.Task;

@Service
public class JobFacade {

	@Autowired
	private JobDao jobDao;
	
	@Autowired 
	private TaskFacade taskFacade; 

	public void addJob(Job j) {
		jobDao.save(j);
	}

	public Job retrieveJob(long id) {
		return this.jobDao.findOne(id);
	}

	public List<Job> retriveAlljobs() {
		return this.jobDao.findAll();
	}
	public void createJob(Job job, Manuscript manuscript, List<Image> imagesTask, Boolean bool,Task task){
		job.setNumberOfWords(imagesTask.size());	
		job.setManuscript(manuscript);
		job.setImages(imagesTask);
		this.addJob(job);
		this.taskFacade.createTask(job, imagesTask.size(), bool,task);
	}
}
