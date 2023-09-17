package com.example.englishclub.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JWTtoken jwTtoken;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		String username = null;
		String jwtTokenString = null;

		if (header != null && header.startsWith("Bearer ")) {

			jwtTokenString = header.substring(7);
			username = jwTtoken.getUsernameFromToken(jwtTokenString);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					username,
					null,
					jwTtoken.getRolesFromToken(jwtTokenString)
			);
			SecurityContextHolder.getContext().setAuthentication(token);
		}
		filterChain.doFilter(request, response);
	}
}
