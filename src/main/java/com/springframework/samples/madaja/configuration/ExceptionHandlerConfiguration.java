package com.springframework.samples.madaja.configuration;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * This advice is necessary because MockMvc is not a real servlet environment, therefore it does not redirect error
 * responses to [ErrorController], which produces validation response. So we need to fake it in tests.
 * It's not ideal, but at least we can use classic MockMvc tests for testing error response + document it.
 */
@ControllerAdvice
public class ExceptionHandlerConfiguration 
{
	@Autowired
	private BasicErrorController basicErrorController;
	// add any exceptions/validations/binding problems
//	@Autowired
//	private CustomErrorController customErrorController;

   @ExceptionHandler(Exception.class)
   public String defaultErrorHandler(HttpServletRequest request,  Exception ex)  {
        request.setAttribute("javax.servlet.error.request_uri", request.getPathInfo());
        request.setAttribute("javax.servlet.error.status_code", 400);
        request.setAttribute("exception", ex);
        return "exception";
    }
}