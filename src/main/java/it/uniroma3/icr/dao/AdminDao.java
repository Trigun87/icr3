package it.uniroma3.icr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.icr.model.Administrator;
@Repository
public interface AdminDao extends JpaRepository<Administrator,Long>{
	public Administrator findByUsername(String username);
}
