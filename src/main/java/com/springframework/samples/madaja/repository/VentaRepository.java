package com.springframework.samples.madaja.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.springframework.samples.madaja.model.Venta;

public interface VentaRepository extends Repository<Venta, Integer>{
	
	Iterable<Venta> findAll() throws DataAccessException; //TODAS las ventas
	
	Venta findById(int id) throws DataAccessException; //Venta por ID
	
	@Query("SELECT venta FROM Venta venta WHERE venta.cliente.dni =:dni") //Encontrar ventas por DNI del cliente
	public List<Venta> findByDniCliente(@Param("dni") String dni);
	
	void save(Venta venta) throws DataAccessException;
	
	@Query("SELECT venta FROM Venta venta WHERE venta.envio.id = :id")
	public Venta findByEnvio(@Param("id") int id);
	
	//PAGINACIÓN
	@Query("SELECT venta FROM Venta venta WHERE venta.cliente.dni =:dni") //Encontrar ventas por DNI del cliente
	public Page<Venta> findAll(@Param("dni") String dni, Pageable pageable);
}
