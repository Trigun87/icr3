package it.uniroma3.icr.dao.impl;


import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.icr.dao.TaskDaoCustom;
import it.uniroma3.icr.model.Task;

@Repository
@Transactional(readOnly=false)
public class TaskDaoImpl implements TaskDaoCustom {


	@PersistenceContext
	private EntityManager entityManager;
		
	public void updateEndDate(Task t) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();

		java.sql.Timestamp date = new java.sql.Timestamp(now.getTime());
		t.setEndDate(date);

		this.entityManager.merge(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> studentsProductivity() {
		String sql = "select student.id, student.name, student.surname, count(*) as numero_task from Task task, Student student where (task.student.id=student.id) group by student.id "
				+ "order by numero_task ";
		Query query = this.entityManager.createQuery(sql);
		List<Object> tasks = query.getResultList();
		return tasks;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> studentsProductivity2() {
		String sql = "select studentsocial.id, studentsocial.name, studentsocial.surname, count(*) as numero_task from Task task, StudentSocial studentsocial where (task.studentsocial.id=studentsocial.id) group by studentsocial.id "
				+ "order by numero_task ";
		Query query = this.entityManager.createQuery(sql);
		List<Object> tasks = query.getResultList();
		return tasks;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> taskTimes() {
		String sql= " select task.job.id,task.batch,to_char(avg(task.endDate - task.startDate), 'HH24:MI:SS:MS') as tempo_medio, to_char(max(task.endDate - task.startDate), 'HH24:MI:SS:MS') "
				+ "as tempo_massimo, to_char(min(task.endDate - task.startDate), 'HH24:MI:SS:MS') as tempo_minimo from Task task where task.endDate IS NOT NULL "
				+ "group by task.job.id,task.batch order by task.job.id,task.batch";
		Query query = this.entityManager.createQuery(sql);
		List<Object> times = query.getResultList();
		System.out.println(times);
		return times;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> majorityVoting() {
		String sql = "select result.image.id, symbol.transcription, count(*) as numero_Yes from Symbol symbol, "
				+ "Job job, Task task, Result result where (symbol.id=job.symbol.id and job.id=task.job.id "
				+ "and task.id = result.task.id) and result.answer='Yes' group by result.image.id, "
				+ "symbol.transcription having count(*)>0 order by symbol.transcription";
		Query query = this.entityManager.createQuery(sql);
		List<Object> voting = query.getResultList();
		return voting;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> symbolAnswers() {
		String sql = "select symbol.transcription, count(*) as task_fatti from Symbol symbol, Job job, "
				+ "Task task, Result result where (symbol.id=job.symbol.id and job.id=task.job.id and "
				+ "task.id = result.task.id) and result.answer='Yes' group by symbol.id order by symbol.transcription";
		Query query = this.entityManager.createQuery(sql);
		List<Object> answers = query.getResultList();
		return answers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> voting() {
		String sql = "select result.image.id, symbol.transcription, count(*) as numero_Yes from Symbol symbol, Job job, Task task, Result result where (symbol.id=job.symbol.id and job.id=task.job.id and task.id = result.task.id) and result.answer='Yes' group by result.image.id, symbol.transcription order by symbol.transcription";
		Query query = this.entityManager.createQuery(sql);
		List<Object> voting = query.getResultList();
		return voting;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> symbolsMajorityAnswers() {
		String sql2 = "select result.image.id, symbol.transcription, count(*) as numero_Yes from Symbol symbol, "
				+ "Job job, Task task, Result result where (symbol.id=job.symbol.id and job.id=task.job.id "
				+ "and task.id = result.task.id) and result.answer='Yes' group by result.image.id, "
				+ "symbol.transcription having count(*)>0 order by symbol.transcription";
		String sql1 = " select count(*) as majorityAnswersVoting  "
				+ " from (select result.image.id, symbol.transcription, count(*) as numero_Yes from Symbol symbol, "
				+ "Job job, Task task, Result result where (symbol.id=job.symbol.id and job.id=task.job.id "
				+ "and task.id = result.task.id) and result.answer='Yes' group by result.image.id, "
				+ "symbol.transcription having count(*)>0 order by symbol.transcription) group by numero_Yes order by numero_Yes";
		Query query = this.entityManager.createQuery(sql1);
		List<Object> majorityAnswers = query.getResultList();
		return majorityAnswers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> correctStudentsAnswers() {
		String sql = "select student.id, student.name, student.surname, count(*) as risposte_corrette from Symbol symbol, Job job, Task task, Result result, (select result.image_id, symbol.transcription, count(*) as numero_Yes from Symbol symbol, Job job, Task task, Result result where (symbol.id=job.symbol_id and job.id=task.job_id and task.id = result.task_id) and result.answer='Yes' group by result.image.id, symbol.transcription having count(*)>1 order by symbol.transcription) as majority where student.id = task.student_id and task.id = result.task_id and majority.image_id=result.image_id group by student.id order by count(*)";
		Query query = this.entityManager.createQuery(sql);
		List<Object> correct = query.getResultList();
		return correct;
	}

}