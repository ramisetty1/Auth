package it.telecom.Authentication.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.telecom.Authentication.entity.User;
import it.telecom.Authentication.pojo.LoginApiData;
import it.telecom.Authentication.pojo.SignUpApiData;
import it.telecom.Authentication.repository.UserRepository;

@Service
public class AuthService {
	
	
	@Autowired
	public UserRepository userRepository;


	
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
	
	
	

}
