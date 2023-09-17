package com.example.englishclub.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JWTtoken {
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.time}")
	private Duration time;

	public String generateToken(UserDetails userDetails){
		Map<String,Object> claims = new HashMap<>();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(role -> role.getAuthority()).toList();

		claims.put("roles",roles);
		claims.put("email",userDetails.getUsername());

		Date issuedDate = new Date();
		Date expiredDate = new Date(issuedDate.getTime()+time.toMillis());

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(issuedDate)
				.setExpiration(expiredDate)
				.signWith(SignatureAlgorithm.HS256,secret)
				.compact();

	}

	public String getUsernameFromToken(String token){
		return getAllClaimsFromToken(token).get("email",String.class);
	}

	public List getRolesFromToken(String token){
		return getAllClaimsFromToken(token).get("roles",List.class);
	}

	private Claims getAllClaimsFromToken(String token){
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}
}
