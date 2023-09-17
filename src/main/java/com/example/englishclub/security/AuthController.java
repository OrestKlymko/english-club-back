//package com.example.englishclub.security;
//
//import com.example.englishclub.user.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Collections;
//
//@RestController
//public class AuthController {
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private JWTtoken jwTtoken;
//	@Autowired
//	private SecurityConfig securityConfig;
//
//
//
//	@PostMapping("/auth")
//	public ResponseEntity createAuthToken(@RequestBody JwtRequest jwtRequest) {
//		try {
//			UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
//			String token = jwTtoken.generateToken(userDetails);
//			return ResponseEntity.ok(token);
//		} catch (BadCredentialsException e) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Невірні облікові дані");
//		}
//	}
//
//}
