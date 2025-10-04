package it.telecom.Authentication.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		
		Map<String, String> errors = new HashMap<String, String>();
		
		ex.getBindingResult().getFieldErrors().forEach(Error -> {
			
			
			errors.put(Error.getField(), Error.getDefaultMessage());
			
			
		});
		Map<String, Object> errorResponse = new HashMap<String, Object>();
		
		errorResponse.put("status", "failed");
		errorResponse.put("message", errors);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenralExceptions(Exception ex) {
		
		Map<String, String> resMap = new HashMap<String, String>();
		
		resMap.put("errmessage", ex.getMessage());
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", "failed");
		
		response.put("data", resMap);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
