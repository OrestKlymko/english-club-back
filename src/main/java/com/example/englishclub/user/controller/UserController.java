package com.example.englishclub.user.controller;


import com.example.englishclub.user.entity.UserEntity;
import com.example.englishclub.user.exception.IncorrectEmailException;
import com.example.englishclub.user.exception.IncorrectPasswordException;
import com.example.englishclub.user.exception.UserAlreadyExistException;
import com.example.englishclub.user.exception.UserNotFoundException;
import com.example.englishclub.user.model.UserRegistrationModel;
import com.example.englishclub.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private UserService userService;


	@PostMapping("/create")
	public ResponseEntity registerNewUser(@RequestBody UserRegistrationModel userRegistrationModel, HttpServletRequest request) {

		try {
			UserEntity userEntity = userService.registerNewCustomer(userRegistrationModel);
			URI uri = URI.create(request.getRequestURI()+"/users/" + userEntity.getId());
			return ResponseEntity.status(HttpStatus.CREATED)
					.location(uri)
					.allow(HttpMethod.POST)
					.contentType(MediaType.APPLICATION_JSON)
					.build();
		} catch (UserAlreadyExistException | IncorrectEmailException | IncorrectPasswordException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.allow(HttpMethod.POST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public void loginUser() {

	}

	@PostMapping("/delete")
	public void deleteUser() {

	}

	@PostMapping("/change/password")
	public void changePassword() {

	}

	@PostMapping("/change/english-level")
	public void updateEnglishLevel() {

	}

	@GetMapping("/{id}")
	public ResponseEntity getUserById(@PathVariable long id) {
		try {
			return ResponseEntity.ok(userService.getUserById(id));
		} catch (UserNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/all")
	public ResponseEntity getUserById() {
		return ResponseEntity.ok(userService.getAll());
	}
}
