package it.uniroma3.icr.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.icr.model.Symbol;
@Repository
public interface SymbolDao extends JpaRepository<Symbol, Long>{
	
	public List<Symbol> findByManuscriptName(String manuscript);
	public Symbol findByTranscriptionAndManuscriptName(String transcription,String manuscript);
}
