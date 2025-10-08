package it.telecom.Authentication.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;
	
	private String name;
	private String email;
	private String password;
	private String mobile_number;
	private String resetPasswordKey;

	
	
	private LocalDateTime createdOn = LocalDateTime.now();
	private boolean isActive = true;
	private boolean isEmailVerified = false;

}
