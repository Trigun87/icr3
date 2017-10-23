package it.uniroma3.icr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.icr.dao.ManuscriptDao;
import it.uniroma3.icr.model.Manuscript;

@Service
public class ManuscriptService {
	@Autowired
	private ManuscriptDao manuscriptDao;
	
	public void saveManuscript(Manuscript manuscript){
		this.manuscriptDao.save(manuscript);
	}
	
	public Manuscript findOneManuscript(Long id){
		return this.manuscriptDao.findOne(id);
	}
	
	public List<Manuscript> findAllManuscript(){
		return this.manuscriptDao.findAll();
	}
	
	public Manuscript findManuscriptByName(String name){
		return this.manuscriptDao.findByName(name);
	}
}
