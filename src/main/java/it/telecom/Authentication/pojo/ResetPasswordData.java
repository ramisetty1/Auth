package it.telecom.Authentication.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordData {
	
	@NotNull(message = "linkid required")
	private String resetPasswordKey;
	
	@NotNull(message = "password required")
	@Size(min = 8, message = "password should be mim 8 char")
	private String password;
	
	@NotNull(message = "password required")
	@Size(min = 8, message = "confirmPassword should be mim 8 char")
	private String confirmPassword;
	

}
