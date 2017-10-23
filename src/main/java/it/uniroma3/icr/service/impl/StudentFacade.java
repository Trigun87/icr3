package it.uniroma3.icr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.StudentDao;
import it.uniroma3.icr.model.Student;

@Service
public class StudentFacade {
	
	@Autowired
	private StudentDao userDao;
	
	public void retrieveUser(Student user) {
		userDao.save(user);
	}
	
	public Student findUser(String username) {
		return this.userDao.findByUsername(username);
	}
	
	public List<Student> retrieveAllStudents() {
		return this.userDao.findAll();
	}
	
	public Student findUserBySurname(String surname) {
		return this.userDao.findBySurname(surname);
	}

}
