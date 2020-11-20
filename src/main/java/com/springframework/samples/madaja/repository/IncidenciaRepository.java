package com.springframework.samples.madaja.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.springframework.samples.madaja.model.Incidencia;

public interface IncidenciaRepository extends Repository<Incidencia, Integer> {
	
	void save(Incidencia incidencia) throws DataAccessException;
	
	@Query("SELECT incidencia FROM Incidencia incidencia WHERE incidencia.id =:id")
	public Incidencia findById(@Param("id") int id);

	Collection<Incidencia> findAll();

}
