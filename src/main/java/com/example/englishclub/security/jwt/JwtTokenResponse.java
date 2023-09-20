package com.example.englishclub.security.jwt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JwtTokenResponse {
	private int status;
	private String username;
	private String body;
	private String token;
}
