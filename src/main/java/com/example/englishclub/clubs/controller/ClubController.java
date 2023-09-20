package com.example.englishclub.clubs.controller;


import com.example.englishclub.clubs.exception.CourseNotFoundException;
import com.example.englishclub.clubs.exception.LimitOfCreationClubByUserException;
import com.example.englishclub.clubs.exception.UserCanNotDeleteClubException;
import com.example.englishclub.clubs.model.ClubCreatingRequestModel;
import com.example.englishclub.clubs.service.ClubService;
import com.example.englishclub.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clubs")
public class ClubController {

	@Autowired
	private ClubService clubService;

	@GetMapping("/get-all")
	public ResponseEntity<Object>  getAllCourse() {
		return ResponseEntity.ok().body(clubService.getAllClubs());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object>  getOneCourse(@PathVariable long id) {
		try {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body(clubService.getOneCourse(id));
		} catch (CourseNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}

	@PostMapping("/create-club")
	public ResponseEntity<Object>  createClubByUser(@RequestBody ClubCreatingRequestModel creatingRequestModel) {

		try {
			clubService.createClubByUser(creatingRequestModel);
			return ResponseEntity.status(HttpStatus.CREATED)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body("Course created");
		} catch (UserNotFoundException | LimitOfCreationClubByUserException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}

	}

	@PostMapping("/delete-club/{id}")
	public ResponseEntity<Object> deleteClubByUser(@PathVariable long id) {
		try {
			clubService.deleteClubByUser(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body("Course created");
		} catch (UserNotFoundException |UserCanNotDeleteClubException | CourseNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}

	}

	@PostMapping("/update-club/{id}")
	public ResponseEntity<Object>  updateClubByUser(@PathVariable long id, @RequestBody ClubCreatingRequestModel model) {
		try {
			clubService.updateClubByUser(id,model);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.contentType(MediaType.APPLICATION_JSON)
					.allow(HttpMethod.POST)
					.body("Course update");
		} catch (UserNotFoundException |UserCanNotDeleteClubException | CourseNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}

	}

}
