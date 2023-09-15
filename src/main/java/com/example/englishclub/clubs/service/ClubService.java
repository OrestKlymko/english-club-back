package com.example.englishclub.clubs.service;

import com.example.englishclub.clubs.entity.ClubEntity;
import com.example.englishclub.clubs.exception.CourseNotFoundException;
import com.example.englishclub.clubs.model.ClubResponseModel;
import com.example.englishclub.clubs.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubService {
	@Autowired
	private ClubRepository clubRepository;

	public List<ClubResponseModel> getAllClubs(){

		List<ClubEntity> all = clubRepository.findAll();
		List<ClubResponseModel> responseList = new LinkedList<>();
		for (ClubEntity clubEntity : all) {
			responseList.add(ClubResponseModel.toModel(clubEntity));
		}
		return responseList;
	}

	public ClubResponseModel getOneCourse(long id) throws CourseNotFoundException {
		Optional<ClubEntity> courseById = clubRepository.findById(id);
		if(courseById.isPresent()){
			return ClubResponseModel.toModel(courseById.get());
		}
		throw new CourseNotFoundException("Course with id "+id+" not found");
	}
}
