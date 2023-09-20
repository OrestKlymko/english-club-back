package com.example.englishclub.user.controller;


import com.example.englishclub.clubs.exception.CourseNotFoundException;
import com.example.englishclub.security.exception.UserNotAuthenticated;
import com.example.englishclub.security.jwt.JwtRequest;
import com.example.englishclub.security.jwt.JwtTokenResponse;
import com.example.englishclub.user.entity.UserEntity;
import com.example.englishclub.user.exception.*;
import com.example.englishclub.user.model.ResponseModel;
import com.example.englishclub.user.model.UserChangePasswordModel;
import com.example.englishclub.user.model.UserRegistrationModel;
import com.example.englishclub.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/registration")
	public ResponseEntity<Object> registerNewUser(@RequestBody UserRegistrationModel userRegistrationModel, HttpServletRequest request) {

		try {
			UserEntity userEntity = userService.registerNewCustomer(userRegistrationModel);
			URI uri = URI.create(request.getRequestURI() + "/users/" + userEntity.getId());
			return ResponseEntity.status(HttpStatus.CREATED)
					.location(uri)
					.allow(HttpMethod.POST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(new ResponseModel(201, "User registration"));
		} catch (UserAlreadyExistException | IncorrectEmailException | IncorrectPasswordException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}

	@PostMapping("/login-user")
	public ResponseEntity<Object> loginUser(@RequestBody JwtRequest jwtRequest) {

		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body(new JwtTokenResponse(202, jwtRequest.getUsername(), "Login successes", userService.loginUser(jwtRequest)));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.contentType(MediaType.APPLICATION_JSON)
					.body(new ResponseModel(500, e.getMessage()));
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<Object> deleteUser() {
		try {
			userService.deleteUser();
			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body(new ResponseModel(201,"User deleted"));
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}

	@PostMapping("/change/password")
	public ResponseEntity<Object> changePassword(@RequestBody UserChangePasswordModel userChangePasswordModel) {
		try {
			userService.changePassword(userChangePasswordModel);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body(new ResponseModel(202, "Password changed"));
		} catch (UserNotFoundException | IncorrectEmailException | IncorrectPasswordException | UserNotAuthenticated |
		         UserPasswordDontMatchException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}

	@PostMapping("change/english-level/{newLevelLanguage}")
	public ResponseEntity<Object> updateEnglishLevel(@PathVariable String newLevelLanguage) {
		try {
			userService.updateEnglishLevelById(newLevelLanguage);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body(new ResponseModel(202, "Language changed"));
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(new ResponseModel(404, e.getMessage()));
		}
	}

	@GetMapping("/get-info")
	public ResponseEntity<Object> getInfoAboutUser() {
		try {
			return ResponseEntity.ok(userService.getUser());
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ResponseModel(500, e.getMessage()));
		}
	}

	@PostMapping("/join/{club_id}")
	public ResponseEntity<Object> joinToCourse(@PathVariable long club_id) {
		try {
			userService.joinToCourse(club_id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body(new ResponseModel(202, "Success joined"));
		} catch (UserNotFoundException | CourseNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}


	@GetMapping("/all")
	public ResponseEntity<Object> getAllUsers() {
		return ResponseEntity.ok(userService.getAll());
	}
}
