package it.uniroma3.icr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.AdminDao;
import it.uniroma3.icr.model.Administrator;

@Service
public class AdminFacade {

	@Autowired
	private AdminDao adminDao;
	public void addAdmin(Administrator admin) {
		adminDao.save(admin);
	}
	
	public Administrator findAdmin(String username){
		return this.adminDao.findByUsername(username);
	}
}
