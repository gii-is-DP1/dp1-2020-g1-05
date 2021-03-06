/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springframework.samples.madaja.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import com.springframework.samples.madaja.model.Cliente;
import com.springframework.samples.madaja.service.ClienteService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Slf4j
@Controller
public class UserController {

	private static final String VIEWS_CLIENTE_CREATE_FORM = "users/createClienteForm";

	private final ClienteService clienteService;

	@Autowired
	public UserController(ClienteService clinicService) {
		this.clienteService = clinicService;
	}
	
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/users/new")
	public String initCreationForm(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		return VIEWS_CLIENTE_CREATE_FORM;
	}

	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid Cliente cliente, BindingResult result) {
		if (result.hasErrors()) {
			log.warn("Errores encontrados en el registro de un usuario: " + result.getAllErrors());
			return VIEWS_CLIENTE_CREATE_FORM;
		}
		else {
			//creando cliente, user, y authority
			this.clienteService.saveCliente(cliente);
			return "redirect:/login";
		}
	}

}
