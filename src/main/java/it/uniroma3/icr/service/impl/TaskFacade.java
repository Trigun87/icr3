package it.uniroma3.icr.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.icr.dao.TaskDao;
import it.uniroma3.icr.model.Image;
import it.uniroma3.icr.model.Job;
import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.Task;

@Service
public class TaskFacade {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TaskDao taskDao;
	@Autowired
	private ResultFacade resultFacade;

	@PersistenceContext
	private EntityManager entityManager;

	public void saveTask(Task t) {
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

		String s = "SELECT t FROM Task t WHERE t.studentsocial.id='" + id + "'";
		Query querys = this.entityManager.createQuery(s);
		List<Task> tasks = querys.getResultList();
		return tasks;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public Task assignTask(Student student) {
		Task task = null;
		String select = null;

		select = "SELECT t "
				+ "FROM Task t "
				+ "WHERE (t.student.id= ?1 AND t.endDate IS NULL AND t.startDate IS NOT NULL)"; // task assegnati allo studente ma lasciati in sospeso 
		Query query1 = this.entityManager.createQuery(select).setParameter(1, student.getId());
		List<Task> taskList = query1.getResultList(); // trova il task da eseguire

		if (taskList.size()!=0) {
			task = taskList.get(0);
			task.setStudent(student);
//			student.addTask(task);  
			LOGGER.info("0 - Resumed task " + task.getId() + " for student " + student.getId());
			return task;
		}
		else {      //if (taskList.size() == 0) {  // non ci sono task in sospeso
			select = "SELECT t FROM Task t "
					+ "WHERE (t.batch, t.job.id) not in ( "                               // task già fatti dallo studente
						+ " SELECT distinct t2.batch, t2.job.id "  
						+ " FROM Task t2 "
						+ " WHERE t2.student.id= ?1 and t2.endDate IS NOT NULL) "
					+ "AND (t.student.id IS NULL)"; // task non assegnati
//			if (student.getId().equals(new Long(194857))) { // e' elena
//				select += " AND t.job.isTutorial = TRUE"; 
//			}
			
			query1 = this.entityManager.createQuery(select).setMaxResults(53).setParameter(1, student.getId());
			taskList = query1.getResultList(); // trova il task da eseguire

			if (taskList.size()!=0) {  // ci sono ancora task
				int position = (int)(student.getId() % taskList.size());
				LOGGER.info("0 - New task " + taskList.get(position).getId() + " to student " + student.getId() );

				Calendar calendar = Calendar.getInstance();
				java.util.Date now = calendar.getTime();
				java.sql.Timestamp date = new java.sql.Timestamp(now.getTime());

				//	this.taskDao.save(task);
				LOGGER.debug("SQL UPDATE update task set start_date = "+ date + " student_id = " + student.getId() + " where id = " + taskList.get(position));

				String update = "update task set start_date = ?1, student_id = ?2 where id = ?3 and student_id is null";
				query1 = this.entityManager.createNativeQuery(update).setParameter(1, date).setParameter(2, student.getId()).setParameter(3, taskList.get(position).getId());
				int numRow = query1.executeUpdate();	
				if (numRow==1) {		
					task = taskList.get(position);
					task.setStudent(student);
					task.setStartDate(date);
					student.addTask(task); 
				}
				else {
					LOGGER.info("0.1 - race on task " + taskList.get(position) + ". Try another task for student " + student.getId());
					task = assignTask(student);
				}
			}
			else { // i task sono finiti
				return null;
			}
		}
		return task;
	}

	/*
	@Transactional
	public Task assignTask(Student s) {
		Task task = null;
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp date = new java.sql.Timestamp(now.getTime());
		String sr1 = "update task t3  set start_date = case when start_date is null then '" + date
				+ "' else start_date end, student_id = ?1 where id = (SELECT id FROM Task t WHERE t.batch not in (SELECT distinct batch FROM Task t2 WHERE t2.student_id= ?2 and t2.end_Date IS NOT NULL) and ((t.student_id= ?3 AND t.end_Date IS NULL) OR (t.student_id IS NULL)) ORDER BY t.student_id LIMIT 1)";
		Query query1 = this.entityManager.createNativeQuery(sr1).setParameter(1, s.getId()).setParameter(2, s.getId())
				.setParameter(3, s.getId());
		LOGGER.info("Update: student " + s.getId());

		if (query1.executeUpdate() == 1) {
			sr1 = "SELECT t FROM Task t WHERE t.batch not in (SELECT distinct batch FROM Task t2 WHERE t2.student.id= ?1 and t2.endDate IS NOT NULL) and ((t.student.id= ?2 AND t.endDate IS NULL) OR (t.student.id IS NULL)) ORDER BY t.student.id";
			query1 = this.entityManager.createQuery(sr1).setMaxResults(1).setParameter(1, s.getId()).setParameter(2, s.getId());


			Task taskList = (Task) query1.getSingleResult(); // trova il task da eseguire
			LOGGER.info("Select: task " + taskList.getId() );
			if (taskList.getStudent()==null) {
				LOGGER.info("Select: student was null for task" + taskList.getId());
			}
			else {
				LOGGER.info("Select: student was " + taskList.getStudent().getId());
			}
			if ((taskList != null) && (taskList.getStudent() != null)) {
				task = taskList;
				task.setStudent(s);
				s.addTask(task);
				LOGGER.info("Assigned task " + task.getId() + " to student " + s.getId() );
			}
		}
		return task;
	}
	 */

	@SuppressWarnings("unchecked")
	public String findHintByTask(Task t) {
		String sr1 = "select answer, count(*) c from result where task_id in (select id from task where batch = ?1 and job_id = ?2) and answer is not null group by answer HAVING count(*) >= ?3 order by c";

		Query query1 = this.entityManager.createNativeQuery(sr1).setMaxResults(1).setParameter(1, t.getBatch())
				.setParameter(2, t.getJob().getId());
		if (t.getJob().isTutorial())
			query1.setParameter(3, 1);
		else
			query1.setParameter(3, 5);
		List<Object[]> temp = query1.getResultList();
		String hint = "";
		if (temp.size() > 0)
			hint = (String) temp.get(0)[0];
		return hint;
	}

	/*
	 * @SuppressWarnings("unchecked") public Task assignTask2(StudentSocial s) {
	 * Task task = null;
	 * 
	 * String sr2 = "SELECT t FROM Task t WHERE t.studentsocial.id='" + s.getId() +
	 * "'" + "AND t.endDate IS NOT NULL"; Query query2 =
	 * this.entityManager.createQuery(sr2); List<Task> tasksByStudent =
	 * query2.getResultList(); // trova i task completati dallo studente
	 * 
	 * String sr3 = "SELECT t FROM Task t WHERE t.studentsocial.id='" + s.getId() +
	 * "'" + "AND t.endDate IS NULL"; Query query3 =
	 * this.entityManager.createQuery(sr3); List<Task> taskList =
	 * query3.getResultList(); // trova i task senza data finale dello studente
	 * 
	 * String sr4 =
	 * "SELECT t FROM Task t WHERE t.studentsocial.id IS NULL AND t.student.id IS NULL"
	 * ; Query query4 = this.entityManager.createQuery(sr4); List<Task> taskList2 =
	 * query4.getResultList(); // trova i task senza studente
	 * 
	 * taskList.addAll(taskList2); // task senza studente+ task senza data finale
	 * 
	 * String sr = "SELECT distinct job_id FROM task WHERE studentsocial_id='" +
	 * s.getId() + "'" + "AND end_date IS NOT NULL"; Query query =
	 * this.entityManager.createNativeQuery(sr); List<BigInteger> jobsIdCompletati =
	 * query.getResultList(); // job effettuati dallo studente
	 * 
	 * for (int i = 0; i < taskList.size(); i++) {
	 * 
	 * Long jobId = taskList.get(i).getJob().getId(); int cont = 0; boolean ver =
	 * false;
	 * 
	 * for (int z = 0; z < jobsIdCompletati.size(); z++) if (jobId ==
	 * jobsIdCompletati.get(z).longValue()) { cont++; Integer batch =
	 * taskList.get(i).getBatch();
	 * 
	 * String sr5 = "SELECT t.studentsocial.id FROM Task t WHERE t.job.id='" + jobId
	 * + "'" + "AND t.studentsocial IS NOT NULL"; Query query5 =
	 * this.entityManager.createQuery(sr5); List<Long> utentiConQuelJob =
	 * query5.getResultList(); String sr7 =
	 * "SELECT t.student.id FROM Task t WHERE t.job.id='" + jobId + "'" +
	 * "AND t.student IS NOT NULL"; Query query7 =
	 * this.entityManager.createQuery(sr7); List<Long> utentiConQuelJob2 =
	 * query7.getResultList();
	 * 
	 * utentiConQuelJob.addAll(utentiConQuelJob2);
	 * 
	 * if (utentiConQuelJob.contains(s.getId())) { String sr6 =
	 * "SELECT t.batch FROM Task t WHERE t.job.id='" + jobId + "'" +
	 * "AND t.studentsocial='" + s.getId() + "'" + "AND end_date IS NOT NULL"; Query
	 * query6 = this.entityManager.createQuery(sr6); List<Integer>
	 * batchesUtenteConQuelJob = query6.getResultList(); boolean o =
	 * batchesUtenteConQuelJob.contains(batch); if (o == false) ver = true; }
	 * 
	 * } if (cont == 0 || ver) {
	 * 
	 * if (false == tasksByStudent.contains(taskList.get(i))) { task =
	 * taskList.get(i); task.setStudentsocial(s); if (task.getStartDate() == null) {
	 * Calendar calendar = Calendar.getInstance(); java.util.Date now =
	 * calendar.getTime(); java.sql.Timestamp date = new
	 * java.sql.Timestamp(now.getTime()); task.setStartDate(date); } break; } }
	 * 
	 * } if (task != null) { s.addTask(task); this.taskDao.save(task); } return
	 * task; }
	 */

	public void updateEndDate(Task t) {
		taskDao.updateEndDate(t);
	}

	// console

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

	@Transactional
	public void createTask(Job job, Integer number, Boolean word, Task task) {
		for (int i = 0; i < job.getNumberOfStudents(); i++) {
			int batchNumber = 0;
			for (int r = 0; r < number; r++) {

				if ((r % job.getTaskSize()) == 0) {
					task = new Task();
					task.setBatch(batchNumber);
					task.setJob(job);
					job.addTask(task);
					this.saveTask(task);
					batchNumber++;
				}
				Image j = job.getImages().get(r);
				Result result = new Result();
				result.setImage(j);
				result.setTask(task);
				this.resultFacade.addResult(result);
			}
		}

	}

	@Transactional
	public void updateStudent(Student student) {

//		LOGGER.debug("SQL UPDATE: update student set task_effettuati " + student.getTaskEffettuati() + ", tempo_effettuato = " + student.getTempoEffettuato() +" where id = " + student.getId());
		String update = "update student set task_effettuati = ?1, tempo_effettuato = ?2 where id = ?3";
		Query query1 = this.entityManager.createNativeQuery(update).setParameter(1, student.getTaskEffettuati()).setParameter(2, student.getTempoEffettuato()).setParameter(3, student.getId());
		if (query1.executeUpdate()!=1)
			LOGGER.debug("PROBLEM IN SQL UPDATE: update student set task_effettuati " + student.getTaskEffettuati() + ", tempo_effettuato = " + student.getTempoEffettuato() +" where id = " + student.getId());
	}

	public List<Result> findTaskResult(Task task, Student student) {
		LOGGER.debug("SQL SELECT r RESULT FROM RESULT WHERE TASK_id = " + task.getId());
		if (task.getStudent().getId()!=student.getId())
			LOGGER.debug("PROBLEM TASK " + task.getId() + " STDUDENT PROBLEM "+ task.getStudent().getId() + " " + student.getId());

		String select = "select r from Result r where r.task.id = ?1";
		Query query1 = this.entityManager.createQuery(select).setParameter(1, task.getId());
		@SuppressWarnings("unchecked")
		List<Result> resultList = query1.getResultList(); // trova i result del task
		return resultList;	
	}

	public Long findStudentIdOnTask(Task task) {
		LOGGER.debug("SQL SELECT t TASK FROM TASK WHERE id = " + task.getId());

		String select = "select t.student.id from Task t where t.id = ?1";
		Query query1 = this.entityManager.createQuery(select).setParameter(1, task.getId());

		Long id = (Long)query1.getSingleResult(); // trova i result del task
		LOGGER.debug("STUDENT FROM DB: = " + id + " for task " + task.getId());
		return id;	
	}
}
