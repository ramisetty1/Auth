package it.telecom.Authentication.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.telecom.Authentication.entity.User;
import it.telecom.Authentication.pojo.LoginApiData;
import it.telecom.Authentication.pojo.SignUpApiData;
import it.telecom.Authentication.service.AuthService;
import jakarta.validation.Valid;

@RestController
public class AuthController {
	
	
	@Autowired
	public AuthService authService;
	
	
	@PostMapping("/create-account")
	public ResponseEntity<Map<String, Object>> createAccount(@Valid @RequestBody SignUpApiData signUpApiData ) throws Exception {
		
		Map<String, Object> resMap = new HashMap<String, Object>();


		
		User response = authService.signUp(signUpApiData);
		resMap.put("status", "success");
		resMap.put("data", response);

		
		return ResponseEntity.status(HttpStatus.OK).body(resMap);

	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginApiData loginApiData) throws Exception {
		
		User dbUser = authService.handleLogin(loginApiData);
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		resMap.put("status", "success");
		resMap.put("data", dbUser);
		
		return ResponseEntity.status(HttpStatus.OK).body(resMap);
		
		
	}
	
	
	
	
	
	
	

}	
