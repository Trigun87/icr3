package it.uniroma3.icr.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.icr.model.Task;
@Repository
public interface TaskDao extends JpaRepository<Task, Long>, TaskDaoCustom{
	public List<Task> findByStudentIsNull();
	public List<Task> findByStudentId(Long id);
}
