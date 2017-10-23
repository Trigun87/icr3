package it.uniroma3.icr.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.icr.model.Result;
import it.uniroma3.icr.model.Task;

@Repository
public interface ResultDao extends JpaRepository<Result, Long>, ResultDaoCustom{

	public List<Result> findByTask(Task task);
}
