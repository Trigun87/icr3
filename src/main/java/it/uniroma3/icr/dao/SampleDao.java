package it.uniroma3.icr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.icr.model.Sample;
@Repository
public interface SampleDao extends JpaRepository<Sample,Long>, SampleDaoCustom {

}
