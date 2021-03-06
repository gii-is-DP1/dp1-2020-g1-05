package com.springframework.samples.madaja.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.springframework.samples.madaja.model.Vehiculos;

public interface VehiculosRepository extends Repository<Vehiculos, Integer>{
	
	void save(Vehiculos vehiculo) throws DataAccessException;
	
	void delete(Vehiculos vehiculo) throws DataAccessException;
	
	Collection<Vehiculos> findAll() throws DataAccessException;
	
	@Query("SELECT vehiculos FROM Vehiculos vehiculos WHERE vehiculos.id =:id")
	public Vehiculos findById(@Param("id") int id);

	@Query("SELECT vehiculos FROM Vehiculos vehiculos WHERE vehiculos.plazas =:plazas")
	public Collection<Vehiculos> findByPlazas(@Param("plazas") int plazas);

	@Query("SELECT vehiculos FROM Vehiculos vehiculos WHERE vehiculos.disponible.id =:id")
	public Collection<Vehiculos> findByDisponible(@Param("id") int id);
	
	@Query("SELECT vehiculos FROM Vehiculos vehiculos WHERE vehiculos.disponible.id < 4 ORDER BY vehiculos.id")
	public Collection<Vehiculos> findAllByDisponible();

	@Query("SELECT vehiculos FROM Vehiculos vehiculos WHERE vehiculos.disponible.id != 4 AND vehiculos.oferta.id IS null")
	public Collection<Vehiculos> findAllVehiculosDisponiblesYsinOfertas();

	@Query("SELECT vehiculos FROM Vehiculos vehiculos WHERE vehiculos.matricula =:matricula")
	public Vehiculos findByMatricula(@Param("matricula") String matricula);
	
	
	@Query("SELECT vehiculos FROM Vehiculos vehiculos WHERE vehiculos.oferta.id =:id")
	public Collection<Vehiculos> findByOferta(@Param("id") int id);

	
	//PAGINACIÓN
	@Query("SELECT vehiculos FROM Vehiculos vehiculos WHERE vehiculos.disponible.id < 4 ORDER BY vehiculos.id")
	public Page<Vehiculos> findAll(Pageable pageable);
	
	//Paginación (disponible)
	@Query("SELECT vehiculos FROM Vehiculos vehiculos WHERE vehiculos.disponible.id =:id")
	public Page<Vehiculos> findAll(@Param("id") int id, Pageable pageable);

}
