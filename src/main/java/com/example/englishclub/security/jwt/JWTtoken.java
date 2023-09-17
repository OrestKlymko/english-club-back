package com.example.englishclub.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Component
public class JWTtoken {
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.time}")
	private Duration time;

	public String generateToken(UserDetails userDetails){
		Map<String,Object> claims = new HashMap<>();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).toList();

		claims.put("roles",roles);
		claims.put("username",userDetails.getUsername());

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
		return getAllClaimsFromToken(token).get("username",String.class);
	}

	public List<GrantedAuthority> getRolesFromToken(String token) {
		List<String> roles = getAllClaimsFromToken(token).get("roles", List.class);
		List<GrantedAuthority> authorities = new ArrayList<>();

		if (roles != null) {
			for (String role : roles) {
				authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			}
		}

		return authorities;
	}

	private Claims getAllClaimsFromToken(String token){
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}
}
