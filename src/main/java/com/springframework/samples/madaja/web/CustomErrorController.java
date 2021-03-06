package com.springframework.samples.madaja.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController{
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	        
	        if(statusCode == HttpStatus.BAD_REQUEST.value()) {
	        	Boolean error400 = true;
	        	log.warn("No se ha definido la url correctamente");
	        	request.setAttribute("error400", error400);
	        }
	        else if(statusCode == HttpStatus.UNAUTHORIZED.value()) {
	        	Boolean error401 = true;
	        	log.warn("No está autorizado para acceder a esta url");
	        	request.setAttribute("error401", error401);
	        }
	        else if(statusCode == HttpStatus.FORBIDDEN.value()) {
	        	Boolean error403 = true;
	        	log.warn("Su acceso a la url está prohibida");
	        	request.setAttribute("error403", error403);
	        }
	        else if(statusCode == HttpStatus.NOT_FOUND.value()) {
	        	Boolean error404 = true;
	        	log.warn("No se ha podido encontrar la url");
	        	request.setAttribute("error404", error404);
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	        	Boolean error500 = true;
	        	log.warn("Ha ocurrido un error inesperado en el servidor");
	        	request.setAttribute("error500", error500);
	        }
	        else if(statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
	        	Boolean error503 = true;
	        	log.warn("El servicio solicitado no está disponible");
	        	request.setAttribute("error503", error503);
	        }
	    }
	    return "exception";
	}

	@Override
	public String getErrorPath() {
		return null;
	}

}
