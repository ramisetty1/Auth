package it.telecom.Authentication.service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;


import it.telecom.Authentication.entity.User;
import it.telecom.Authentication.pojo.ForgetPasswordApiData;
import it.telecom.Authentication.pojo.LoginApiData;
import it.telecom.Authentication.pojo.ResetPasswordData;
import it.telecom.Authentication.pojo.SignUpApiData;
import it.telecom.Authentication.repository.UserRepository;


@Service
public class AuthService {
	
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public JavaMailSender mailSender;
	
	@Autowired
	public TemplateEngine templateEngine;
	
	@Autowired
	public EmailService emailService;
	
	


	
	public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	
	public User signUp(SignUpApiData signUpApiData) throws Exception {
		
		if ( userRepository.findByEmail(signUpApiData.getEmail()).isEmpty() ) {
			
			User user = new User();
			
			user.setName(signUpApiData.getName());
			user.setEmail(signUpApiData.getEmail());
			user.setPassword(passwordEncoder.encode(signUpApiData.getPassword()));
			//user.setPassword(signUpApiData.getPassword());
			user.setMobile_number(signUpApiData.getMobileNumber());
			
			
			User dbUser =	userRepository.save(user);
			
			return dbUser;
			
		}else {

			throw new Exception("User already exist. please login");
			
		}	
		
	}
	
	public User handleLogin(LoginApiData loginApiData) throws Exception {
		
		Optional<User> dbData	= userRepository.findByEmail(loginApiData.getEmail());
		
		if (dbData.isEmpty()==true) {
			
			throw new Exception("user not registered with us. please sign up");
		}
		
		else {
			User dbUser = dbData.get();
		    Boolean isMatching=	passwordEncoder.matches(loginApiData.getPassword(), dbUser.getPassword());
		    
		    if (isMatching==true) {
		    	
		    	return dbUser;
		    }
		    
		    else {
				throw new Exception("password is not matching. pls retry");
			}
			
		}

		
	}
	

	
	public void handleForgetPassword(ForgetPasswordApiData forgetPasswordApiData) throws Exception {
		
		Optional<User> userData	= userRepository.findByEmail(forgetPasswordApiData.getEmail());
		
		if (userData.isEmpty()) {
			throw new Exception("user not registered. please signup");
		}
		else {
			System.out.println(userData.get());
			System.out.println(UUID.randomUUID().toString());
			
			User user = userData.get();
			
			String passwordResetKey=UUID.randomUUID().toString();	
			user.setResetPasswordKey(UUID.randomUUID().toString());
			
			userRepository.save(user);
			
			String emailBody = "Hi " + user.getName() + ",<br><br>"
			        + "Please find below link to reset your password:<br><br>"
			        + "<a href=\"http://localhost:8080/reset-password?linkid=" + passwordResetKey + "\">Click here</a>";
	
			
			emailService.htmlMail("toramisetty1993@gmail.com", forgetPasswordApiData.getEmail(), "Reset Password", emailBody);
			
			
		}
		
		
	}
	
	//validated, check password and confirm password are same or not
	public void handleResetpassword(ResetPasswordData resetPasswordData) throws Exception {
		
		if (resetPasswordData.getPassword().equals(resetPasswordData.getConfirmPassword())==false) {
			
			throw new Exception("Password not matched. try again");
			
			
		}
		
		
			
		Optional<User> db = userRepository.findByResetPasswordKey(resetPasswordData.getResetPasswordKey());
		
		
		if (db.isEmpty()==true) {
			throw new Exception("password expired");
		}
		
		User userData = db.get();
		System.out.println(db.get());
		
		userData.setPassword(passwordEncoder.encode(resetPasswordData.getPassword()));
		userData.setResetPasswordKey("");
		userRepository.save(userData);
	
		
	}
	
	
	
	
	
	
	
	

}
