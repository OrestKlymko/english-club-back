package com.example.englishclub.user.controller;


import com.example.englishclub.clubs.exception.CourseNotFoundException;
import com.example.englishclub.security.exception.UserNotAuthenticated;
import com.example.englishclub.security.jwt.JwtRequest;
import com.example.englishclub.user.entity.UserEntity;
import com.example.englishclub.user.exception.IncorrectEmailException;
import com.example.englishclub.user.exception.IncorrectPasswordException;
import com.example.englishclub.user.exception.UserAlreadyExistException;
import com.example.englishclub.user.exception.UserNotFoundException;
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




	@PostMapping("/create")
	public ResponseEntity registerNewUser(@RequestBody UserRegistrationModel userRegistrationModel, HttpServletRequest request) {

		try {
			UserEntity userEntity = userService.registerNewCustomer(userRegistrationModel);
			URI uri = URI.create(request.getRequestURI() + "/users/" + userEntity.getId());
			return ResponseEntity.status(HttpStatus.CREATED)
					.location(uri)
					.allow(HttpMethod.POST)
					.contentType(MediaType.APPLICATION_JSON)
					.build();
		} catch (UserAlreadyExistException | IncorrectEmailException | IncorrectPasswordException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}

	@PostMapping("/login-user")
	public ResponseEntity loginUser(@RequestBody JwtRequest jwtRequest) {
		try {

			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body(userService.loginUser(jwtRequest));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@PostMapping("/delete")
	public ResponseEntity deleteUser() {
		try {
			userService.deleteUser();
			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body("User deleted");
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}

	@PostMapping("/change/password")
	public ResponseEntity changePassword(UserChangePasswordModel userChangePasswordModel) {
		try {
			userService.changePassword(userChangePasswordModel);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body("Password changed");
		} catch (UserNotFoundException | IncorrectEmailException | IncorrectPasswordException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}

	@PostMapping("change/english-level/{newLevelLanguage}")
	public ResponseEntity updateEnglishLevel(@PathVariable String newLevelLanguage) {
		try {
			userService.updateEnglishLevelById(newLevelLanguage);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body("Language changed");
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}

	@GetMapping("/get-info")
	public ResponseEntity getInfoAboutUser() {
		try {
			return ResponseEntity.ok(userService.getUser());
		} catch (UserNotFoundException | UserNotAuthenticated e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}

	@PostMapping("/join/{club_id}")
	public ResponseEntity joinToCourse(@PathVariable long club_id) {
		try {
			userService.joinToCourse(club_id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body("Success joined");
		} catch (UserNotFoundException | CourseNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}


	@GetMapping("/all")
	public ResponseEntity getAllUsers() {
		return ResponseEntity.ok(userService.getAll());
	}
}
