package com.example.englishclub.clubs.controller;


import com.example.englishclub.clubs.exception.CourseNotFoundException;
import com.example.englishclub.clubs.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clubs")
public class ClubController {

	@Autowired
	private ClubService clubService;


	@GetMapping("/get-all")
	public ResponseEntity getAllCourse() {
		return ResponseEntity.ok().body(clubService.getAllClubs());
	}

	@GetMapping("/{id}")
	public ResponseEntity getOneCourse(@PathVariable long id) {
		try {
			return ResponseEntity.ok().body(clubService.getOneCourse(id));
		} catch (CourseNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(e.getMessage());
		}
	}

}
