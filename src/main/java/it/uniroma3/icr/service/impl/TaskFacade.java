package it.uniroma3.icr.service.impl;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.TaskDao;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.StudentSocial;
import it.uniroma3.icr.model.Task;


@Service
public class TaskFacade {

	@Autowired
	private TaskDao taskDao;
	@Autowired
	private ResultFacade resultFacade;
	
	@PersistenceContext
	private EntityManager entityManager;

	public void addTask(Task t) {
		taskDao.save(t);
	}

	public Task retrieveTask(long id) {
		return this.taskDao.findOne(id);
	}

	public List<Task> retrieveAllTask() {
		return this.taskDao.findAll();
	}

	public List<Task> findTaskByStudent(Long id) {
		return this.taskDao.findByStudentId(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Task> findTaskByStudentSocial(Long id) {
		
		String s = "SELECT t FROM Task t WHERE t.studentsocial.id='"+ id +"'";
		Query querys= this.entityManager.createQuery(s);
		List<Task> tasks = querys.getResultList(); 
		return tasks;
	}

	@SuppressWarnings("unchecked")
	public Task assignTask(Student s) {
		Task task = null;
		
			String sr2 = "SELECT t FROM Task t WHERE t.student.id='"+ s.getId()+"'"+"AND t.endDate IS NOT NULL";
			Query query2= this.entityManager.createQuery(sr2);
			List<Task> tasksByStudent = query2.getResultList(); //trova i task completati  dallo studente
			
			String sr3 = "SELECT t FROM Task t WHERE t.student.id='"+s.getId()+"'"+ "AND t.endDate IS NULL";
			Query query3= this.entityManager.createQuery(sr3);
			List<Task> taskList = query3.getResultList(); //trova i task senza data finale dello studente
			
			String sr4 = "SELECT t FROM Task t WHERE t.student.id IS NULL AND t.studentsocial.id IS NULL";
			Query query4= this.entityManager.createQuery(sr4);
			List<Task> taskList2 = query4.getResultList(); //trova i task senza studente
			
			taskList.addAll(taskList2); // task senza studente+ task senza data finale
			
			String sr = "SELECT job_id FROM task WHERE student_id='"+ s.getId()+"'"+"AND end_date IS NOT NULL";
			Query query= this.entityManager.createNativeQuery(sr);
			List<BigInteger> jobsIdCompletati = query.getResultList(); //job effettuati dallo studente
			
		
			
	for(int i=0; i<taskList.size();i++){
				
				Long jobId= taskList.get(i).getJob().getId();
				int cont=0;
				boolean ver=false;
				
				for(int z=0;z<jobsIdCompletati.size();z++)
				if(jobId==jobsIdCompletati.get(z).longValue()) {
					cont++;
					Integer batch= taskList.get(i).getBatch();
					
						
					String sr5 = "SELECT t.studentsocial.id FROM Task t WHERE t.job.id='"+jobId+"'"+"AND t.studentsocial IS NOT NULL";
					Query query5= this.entityManager.createQuery(sr5);
					List<Long> utentiConQuelJob = query5.getResultList(); 
					String sr7 = "SELECT t.student.id FROM Task t WHERE t.job.id='"+jobId+"'"+"AND t.student IS NOT NULL";
					Query query7= this.entityManager.createQuery(sr7);
					List<Long> utentiConQuelJob2 = query7.getResultList(); 
					
					utentiConQuelJob.addAll(utentiConQuelJob2); 
						
						if(utentiConQuelJob.contains(s.getId())) {
							String sr6 = "SELECT t.batch FROM Task t WHERE t.job.id='"+jobId+"'"+"AND t.student='"+ s.getId()+"'"+"AND end_date IS NOT NULL";
							Query query6= this.entityManager.createQuery(sr6);
							List<Integer> batchesUtenteConQuelJob = query6.getResultList(); 
							boolean o=batchesUtenteConQuelJob.contains(batch);
							if(o==false)
							 ver=true;
						}
					
						
					
				}
				if(cont==0 || ver){
				
					if(false==tasksByStudent.contains(taskList.get(i))){
						task = taskList.get(i);
						task.setStudent(s);
						if(task.getStartDate()==null) {
						Calendar calendar = Calendar.getInstance();
						java.util.Date now = calendar.getTime();
						java.sql.Timestamp date = new java.sql.Timestamp(now.getTime());
						task.setStartDate(date);
						}

						break;
					}	
				}
				
			
			}
			if(task!=null){
				s.addTask(task);
				this.taskDao.save(task);
			}
		

		
		return task;
	}

	
	
	@SuppressWarnings("unchecked")
	public Task assignTask2(StudentSocial s) {
		Task task = null;
		
			String sr2 = "SELECT t FROM Task t WHERE t.studentsocial.id='"+ s.getId()+"'"+"AND t.endDate IS NOT NULL";
			Query query2= this.entityManager.createQuery(sr2);
			List<Task> tasksByStudent = query2.getResultList(); //trova i task completati  dallo studente
			
			String sr3 = "SELECT t FROM Task t WHERE t.studentsocial.id='"+s.getId()+"'"+ "AND t.endDate IS NULL";
			Query query3= this.entityManager.createQuery(sr3);
			List<Task> taskList = query3.getResultList(); //trova i task senza data finale dello studente
			
			String sr4 = "SELECT t FROM Task t WHERE t.studentsocial.id IS NULL AND t.student.id IS NULL";
			Query query4= this.entityManager.createQuery(sr4);
			List<Task> taskList2 = query4.getResultList(); //trova i task senza studente
			
			taskList.addAll(taskList2); // task senza studente+ task senza data finale
			
			String sr = "SELECT distinct job_id FROM task WHERE studentsocial_id='"+ s.getId()+"'"+"AND end_date IS NOT NULL";
			Query query= this.entityManager.createNativeQuery(sr);
			List<BigInteger> jobsIdCompletati = query.getResultList(); //job effettuati dallo studente
			
		
			
	for(int i=0; i<taskList.size();i++){
				
				Long jobId= taskList.get(i).getJob().getId();
				int cont=0;
				boolean ver=false;
				
				for(int z=0;z<jobsIdCompletati.size();z++)
				if(jobId==jobsIdCompletati.get(z).longValue()) {
					cont++;
					Integer batch= taskList.get(i).getBatch();
					
						
						String sr5 = "SELECT t.studentsocial.id FROM Task t WHERE t.job.id='"+jobId+"'"+"AND t.studentsocial IS NOT NULL";
						Query query5= this.entityManager.createQuery(sr5);
						List<Long> utentiConQuelJob = query5.getResultList(); 
						String sr7 = "SELECT t.student.id FROM Task t WHERE t.job.id='"+jobId+"'"+"AND t.student IS NOT NULL";
						Query query7= this.entityManager.createQuery(sr7);
						List<Long> utentiConQuelJob2 = query7.getResultList(); 
						
						utentiConQuelJob.addAll(utentiConQuelJob2);
						
						if(utentiConQuelJob.contains(s.getId())) {
							String sr6 = "SELECT t.batch FROM Task t WHERE t.job.id='"+jobId+"'"+"AND t.studentsocial='"+ s.getId()+"'"+"AND end_date IS NOT NULL";
							Query query6= this.entityManager.createQuery(sr6);
							List<Integer> batchesUtenteConQuelJob = query6.getResultList(); 
							boolean o=batchesUtenteConQuelJob.contains(batch);
							if(o==false)
							 ver=true;
						}
					
						
					
				}
				if(cont==0 || ver){
				
					if(false==tasksByStudent.contains(taskList.get(i))){
						task = taskList.get(i);
						task.setStudentsocial(s);
						if(task.getStartDate()==null) {
						Calendar calendar = Calendar.getInstance();
						java.util.Date now = calendar.getTime();
						java.sql.Timestamp date = new java.sql.Timestamp(now.getTime());
						task.setStartDate(date);
						}	
						break;
					}
				}
				
			
			}
			if(task!=null){
				s.addTask(task);
				this.taskDao.save(task);
			}
			return task;
		}

		
		
	
	
	
	
	
	public void updateEndDate(Task t) {
		taskDao.updateEndDate(t);
	}	

	//console

	public List<Object> studentsProductivity() {
		return this.taskDao.studentsProductivity();
	}
	public List<Object> studentsProductivity2() {
		return this.taskDao.studentsProductivity2();
	}

	public List<Object> taskTimes() {
		return this.taskDao.taskTimes();
	}

	public List<Object> majorityVoting() {
		return this.taskDao.majorityVoting();
	}

	public List<Object> symbolAnswers() {
		return this.taskDao.symbolAnswers();
	}

	public List<Object> voting() {
		return this.taskDao.voting();
	}

	public List<Object> symbolsMajorityAnswers() {
		return this.taskDao.symbolsMajorityAnswers();
	}

	public List<Object> correctStudentsAnswers() {
		return this.taskDao.correctStudentsAnswers();
	}

	public void createTask(Job job, Integer number, Boolean word,Task task) {
		for(int i = 0; i<job.getNumberOfStudents() ; i++){
			int batchNumber = 0;
			for(int r = 0 ; r<number ; r++) {
				
				if ((r % job.getTaskSize())==0) {

					task = new Task();
					task.setBatch(batchNumber);
					task.setJob(job);
					job.addTask(task);
					this.addTask(task);
					batchNumber++;
				}
				if(word){
					List<Image> images =job.getWords().get(r).getImages();

					for(int j = 0; j<images.size() ; j++ ){
						Result result = new Result();
						result.setImage(images.get(j));
						result.setTask(task);
						this.resultFacade.addResult(result);				
					}
				}else{
					Image j = job.getImages().get(r);
					Result result = new Result();
					result.setImage(j);
					result.setTask(task);
					this.resultFacade.addResult(result);
				}
			}
		}
	}

}
