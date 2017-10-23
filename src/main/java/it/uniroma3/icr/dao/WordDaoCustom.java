package it.uniroma3.icr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.uniroma3.icr.model.Word;
@Repository
public interface WordDaoCustom {

	public List<Word> findWordForManuscriptName(String manuscript, int limit);
	
	public List<String> findAllManuscript();
	
	public List<String> findAllPages();

	public Object[] countWord();
}
