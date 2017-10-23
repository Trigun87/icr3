package it.uniroma3.icr.dao;

import java.util.List;
import org.springframework.stereotype.Repository;

import it.uniroma3.icr.model.Sample;

@Repository
public interface NegativeSampleDaoCustom {
	public List<Sample> findAllNegativeSamplesBySymbolId(long id);
}
