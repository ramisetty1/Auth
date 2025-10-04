package it.telecom.Authentication.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginApiData {
	
	
	@NotNull(message = "Email required")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "invalid email format")
	private String email;
	
	
	@NotNull(message = "password required")
	@Size(min = 8, message = "password should be mim 8 char")
	private String password;

}
