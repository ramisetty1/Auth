package it.telecom.Authentication.controller;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.ServerRequest.Headers;

import io.jsonwebtoken.Header;
import it.telecom.Authentication.entity.User;
import it.telecom.Authentication.pojo.ForgetPasswordApiData;
import it.telecom.Authentication.pojo.LoginApiData;
import it.telecom.Authentication.pojo.ResetPasswordData;
import it.telecom.Authentication.pojo.SignUpApiData;
import it.telecom.Authentication.service.AuthService;
import it.telecom.Authentication.service.EmailService;
import jakarta.validation.Valid;

@RestController
public class AuthController {
	
	@Autowired
	public EmailService emailService;
	
	
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
		
		Map<String, Object> dbUser = authService.handleLogin(loginApiData);
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		resMap.put("status", "success");
		resMap.put("data", dbUser);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", dbUser.get("token").toString());
		
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resMap);
		
		
	}
	
	
	@GetMapping("/plain-email")
	public ResponseEntity<Map<String, String>> planiTextEmail() throws Exception {
		
		String fromEmail = "toramisetty1993@gmail.com";
		String toEmail = "toramisetty1993@gmail.com";
		
		String subject = "Planin text email body";
		String body = "Hi, this is from text email";
		
		
		
		//authService.textMail(fromEmail, toEmail, subject, body);
		
		body = "Hi, siva <br/>" 
				+ "this is from html <br/>"
				+ "regards <br/>"
				+ "siva <br/>";
				
		
		emailService.htmlMail(fromEmail, toEmail, subject, body);
		
		//emailService.templateMail(fromEmail, toEmail, subject, "index");
		
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("status", "success")	;
		return ResponseEntity.status(HttpStatus.OK).body(resMap);
		
		
	}
	//path, data validation,
	@PostMapping("/forget-password")
	public  ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordApiData forgetPasswordApiData) throws Exception{
		authService.handleForgetPassword(forgetPasswordApiData);
		
		Map<String, String> responseMap = new HashMap<String, String>();
		
		responseMap.put("result", "success");
		responseMap.put("message", "reset password sent to your email. please check");
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
	
	@PostMapping("/reset-password")
	public  ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordData resetPasswordData) throws Exception{
		authService.handleResetpassword(resetPasswordData);
		
		Map<String, String> responseMap = new HashMap<String, String>();
		
		responseMap.put("result", "success");
		responseMap.put("message", "password updated successfully");
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
	
	
	
	
	
	
	
	
	
	
	

}	
