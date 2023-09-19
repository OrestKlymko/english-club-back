package com.example.englishclub.clubs.service;

import com.example.englishclub.clubs.entity.ClubEntity;
import com.example.englishclub.clubs.exception.CourseNotFoundException;
import com.example.englishclub.clubs.exception.LimitOfCreationClubByUserException;
import com.example.englishclub.clubs.exception.UserCanNotDeleteClubException;
import com.example.englishclub.clubs.model.ClubCreatingRequestModel;
import com.example.englishclub.clubs.model.ClubResponseModel;
import com.example.englishclub.clubs.repository.ClubRepository;
import com.example.englishclub.security.SecurityConfig;
import com.example.englishclub.user.entity.UserEntity;
import com.example.englishclub.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClubService {
	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private SecurityConfig securityConfig;


	public List<ClubResponseModel> getAllClubs() {

		List<ClubEntity> all = clubRepository.findAll();
		List<ClubResponseModel> responseList = new LinkedList<>();
		for (ClubEntity clubEntity : all) {
			responseList.add(ClubResponseModel.toModel(clubEntity));
		}
		return responseList;
	}

	public ClubResponseModel getOneCourse(long id) throws CourseNotFoundException {
		ClubEntity courseById = clubRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("Course with id " + id + " not found"));
			return ClubResponseModel.toModel(courseById);

	}

	public void createClubByUser(ClubCreatingRequestModel newClub) throws UserNotFoundException, LimitOfCreationClubByUserException {
		UserEntity authenticatedUser = securityConfig.getAuthenticatedUser();
		if (authenticatedUser.getCreationClubs().size() == 3) {
			throw new LimitOfCreationClubByUserException("You reach a limit of creating club. Limit is 3 per user");
		}
		Set<UserEntity> clubEntitySet = new HashSet<>();
		ClubEntity newClubCreation = ClubEntity.builder()
				.userCreation(authenticatedUser)
				.clubDescription(newClub.getClubDescription())
				.clubName(newClub.getClubName())
				.timeEnd(newClub.getTimeEnd())
				.timeStart(newClub.getTimeStart())
				.levelEnglish(newClub.getLevelEnglish())
				.themesType(newClub.getThemesType())
				.currentDate(newClub.getCurrentDate())
				.userExists(clubEntitySet)
				.build();
		clubRepository.save(newClubCreation);
	}

	public void deleteClubByUser(long id) throws UserNotFoundException, CourseNotFoundException, UserCanNotDeleteClubException {
		UserEntity authenticatedUser = securityConfig.getAuthenticatedUser();
		ClubEntity clubEntity = clubRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("Course with id " + id + " not found"));
		for (ClubEntity creationClub : authenticatedUser.getCreationClubs()) {
			if(creationClub.equals(clubEntity)){
				clubRepository.delete(clubEntity);
				return;
			}
		}
		throw new UserCanNotDeleteClubException("User can't delete this club. Not enough permission");
	}

	public void updateClubByUser(long id, ClubCreatingRequestModel updateClub) throws CourseNotFoundException, UserNotFoundException, UserCanNotDeleteClubException {
		ClubEntity clubEntityFound = clubRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("Course with id " + id + " not found"));
		UserEntity authenticatedUser = securityConfig.getAuthenticatedUser();

		ClubEntity buildUpdateClub = ClubEntity.builder()
				.userExists(clubEntityFound.getUserExists())
				.userCreation(authenticatedUser)
				.clubName(updateClub.getClubName())
				.clubDescription(updateClub.getClubDescription())
				.timeEnd(updateClub.getTimeEnd())
				.timeStart(updateClub.getTimeStart())
				.currentDate(updateClub.getCurrentDate())
				.themesType(updateClub.getThemesType())
				.levelEnglish(updateClub.getLevelEnglish())
				.build();

		for (ClubEntity club : authenticatedUser.getCreationClubs()) {
			if(club.equals(clubEntityFound)){
				clubRepository.save(buildUpdateClub);
				return;
			}
		}
		throw new UserCanNotDeleteClubException("User can't delete this club. Not enough permission");
	}
}
