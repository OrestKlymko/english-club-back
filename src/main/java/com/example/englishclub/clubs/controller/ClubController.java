package com.example.englishclub.clubs.controller;



import com.example.englishclub.clubs.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clubs")
public class ClubController {

	@Autowired
	private ClubService clubService;


	@GetMapping("/get-all")
	public ResponseEntity getAllCourse(){
		return ResponseEntity.ok().body(clubService.getAllClubs());
	}
}
