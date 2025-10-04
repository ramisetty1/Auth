package it.telecom.Authentication.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpApiData {
	
	
	@NotNull(message = "Name required")
	@Size(min = 3, message = "Name should be minimum 3 characters")
	private String name;
	
	
	@NotNull(message = "Email required")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "invalid email format")
	private String email;
	
	@NotNull(message = "password required")
	@Size(min = 8, message = "password should be minimum 8 characters")
	private String password;
	
	@NotNull(message = "mobileNumber required")
	@Size(min = 10, message = "mobileNumber should be minimum 10 characters")
	private String mobileNumber;

}
