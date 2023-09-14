package com.example.englishclub.controller;


import com.example.englishclub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	@Autowired
	private UserService userService;


	@PostMapping("/create")
	public void registerNewUser() {

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
		return ResponseEntity.ok(userService.getUserById(id));
	}


	@GetMapping("/all")
	public ResponseEntity getUserById() {
		return ResponseEntity.ok(userService.getAll());
	}
}
