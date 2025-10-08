package it.telecom.Authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.telecom.Authentication.entity.User;
import java.util.Optional;
import java.util.List;



@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByResetPasswordKey(String resetPasswordKey);

	

}
