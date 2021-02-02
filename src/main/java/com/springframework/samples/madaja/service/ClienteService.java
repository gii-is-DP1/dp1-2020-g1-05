package com.springframework.samples.madaja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springframework.samples.madaja.model.Cliente;
import com.springframework.samples.madaja.model.User;
import com.springframework.samples.madaja.repository.ClienteRepository;

@Service
public class ClienteService {
	private ClienteRepository clienteRepository;
	private UserService userService;
	private AuthoritiesService authoritiesService;
	
	@Autowired
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	@Transactional(readOnly = true)
	public Cliente findClienteByDni(String id) throws DataAccessException {
		return clienteRepository.findByDni(id);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Cliente> findAllClientes() throws DataAccessException{
		return clienteRepository.findAll();
	}
	
	//////
	@Transactional(readOnly = true)
	public Page<Cliente> getAll(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}
	//////
	
	@Transactional
	public void saveCliente(Cliente cliente) throws DataAccessException {
		//creating cliente
		clienteRepository.save(cliente);		
		//creating user
		User sd = cliente.getUser();
		userService.saveUser(cliente.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
	}
	
	@Transactional
	public Cliente findClienteByUsername(String username) {
		return clienteRepository.findByUsername(username);
	}
 }
