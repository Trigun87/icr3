package it.uniroma3.icr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.StudentDao;
import it.uniroma3.icr.dao.StudentDaoSocial;
import it.uniroma3.icr.model.Student;
import it.uniroma3.icr.model.StudentSocial;

@Service
public class StudentFacadeSocial {
	
	@Autowired
	private StudentDaoSocial userDao;
	
	public void retrieveUser(StudentSocial user) {
		userDao.save(user);
	}
	
	public StudentSocial findUser(String username) {
		return this.userDao.findByUsername(username);
	}
	
	public List<StudentSocial> retrieveAllStudents() {
		return this.userDao.findAll();
	}
	
	public StudentSocial findUserBySurname(String surname) {
		return this.userDao.findBySurname(surname);
	}

}
