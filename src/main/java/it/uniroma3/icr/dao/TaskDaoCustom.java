package it.uniroma3.icr.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import it.uniroma3.icr.model.Task;

@Repository
public interface TaskDaoCustom {

	public void updateEndDate(Task t);
	public List<Object> studentsProductivity();
	public List<Object> studentsProductivity2();
	public List<Object> taskTimes();
	public List<Object> majorityVoting();
	public List<Object> symbolAnswers();
	public List<Object> voting();
	public List<Object> symbolsMajorityAnswers();
	public List<Object> correctStudentsAnswers();
}
