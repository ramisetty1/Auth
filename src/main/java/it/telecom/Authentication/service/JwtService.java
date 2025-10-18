package it.telecom.Authentication.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.auditing.config.IsNewAwareAuditingHandlerBeanDefinitionParser;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.telecom.Authentication.entity.User;

@Service
public class JwtService {
	
	
	@Value("${jwt.secret.key}")
	private String JWT_SECRET_KEY;
	
	private int  TokenExpiraryTime = 24*60*60*1000;
	
	private Key generateSecurityKey() {
		
		return Keys.hmacShaKeyFor(JWT_SECRET_KEY.getBytes());
		
	}
	
	public String genarateJwtToken(User dbUser) {
		
		Date tokenGenerateTime = new Date();
		
		Date expiraryDate = new Date(tokenGenerateTime.getTime() + TokenExpiraryTime);
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("id", dbUser.getId());
		resMap.put("email", dbUser.getEmail());
		resMap.put("name", dbUser.getName());
		
		String jwtToken	 = 
				Jwts.builder().
		claims().add(resMap).and()
		.subject(dbUser.getName())
		.issuedAt(tokenGenerateTime)
		.expiration(expiraryDate)
		.signWith(generateSecurityKey()).compact();
		
		return jwtToken;
		
		
	}
	
	public Claims getJwtClaims(String token) {
		
		SecretKey secretKey = new SecretKeySpec(JWT_SECRET_KEY.getBytes(), "HmacSHA256");
		
		Claims claims =	Jwts.parser()
		.verifyWith(secretKey)
		.build()
		.parseSignedClaims(token)
		.getPayload();
		
		return claims;
		
		
	}
	
	public boolean getJwtValidate(String token) {
		Claims claims =	getJwtClaims(token);
		
		boolean isValid =	claims.getExpiration().after(new Date());
		return isValid;
		
	
		
	}
	

}
