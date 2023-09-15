package com.example.englishclub.user.controller;


import com.example.englishclub.clubs.exception.CourseNotFoundException;
import com.example.englishclub.user.entity.UserEntity;
import com.example.englishclub.user.exception.IncorrectEmailException;
import com.example.englishclub.user.exception.IncorrectPasswordException;
import com.example.englishclub.user.exception.UserAlreadyExistException;
import com.example.englishclub.user.exception.UserNotFoundException;
import com.example.englishclub.user.model.UserChangePasswordModel;
import com.example.englishclub.user.model.UserLoginModel;
import com.example.englishclub.user.model.UserRegistrationModel;
import com.example.englishclub.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;


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

	@PostMapping("/login")
	public ResponseEntity loginUser(@RequestBody UserLoginModel userLoginModel, HttpServletRequest request) {
		try {
			userService.login(userLoginModel);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body("Login success");
		} catch (UserNotFoundException | IncorrectPasswordException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}

	@PostMapping("/delete/{id}")
	public ResponseEntity deleteUser(@PathVariable long id) {
		try {
			userService.deleteUser(id);
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

	@PostMapping("{id}/change/english-level/{newLanguage}")
	public ResponseEntity updateEnglishLevel(@PathVariable int id, @PathVariable String newLanguage) {
		try {
			userService.updateEnglishLevelById(id, newLanguage);
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

	@GetMapping("/{id}")
	public ResponseEntity getUserById(@PathVariable long id) {
		try {
			return ResponseEntity.ok(userService.getUserById(id));
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}

	@PostMapping("/join/{user_id}/{club_id}")
	public ResponseEntity joinToCourse(@PathVariable long user_id, @PathVariable long club_id) {
		try {
			userService.joinToCourse(club_id, user_id);
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
	public ResponseEntity getUserById() {
		return ResponseEntity.ok(userService.getAll());
	}
}
