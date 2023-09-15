package com.example.englishclub.user.model;

import com.example.englishclub.clubs.entity.ClubEntity;
import com.example.englishclub.clubs.model.ClubOnUserResponseModel;
import com.example.englishclub.user.entity.UserEntity;
import com.example.englishclub.user.entity.enums.LevelEnglish;
import com.example.englishclub.user.entity.enums.ThemesType;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class UserResponseModel {
	private long id;
	private String email;
	private String username;
	private LevelEnglish levelOfEnglish;
	private String country;
	private ThemesType themesType;
	private Set<ClubOnUserResponseModel> existClubs;
	private Set<ClubOnUserResponseModel> creatingClubsByUser;

	public static UserResponseModel toModel(UserEntity user) {
		Set<ClubOnUserResponseModel> existClubList = new HashSet<>();
		Set<ClubOnUserResponseModel> creatingClubsByUserList = new HashSet<>();
		for (ClubEntity club : user.getExistClubs()) {
			existClubList.add(ClubOnUserResponseModel.toModel(club));
		}

		for (ClubEntity club : user.getCreationClubs()) {
			creatingClubsByUserList.add(ClubOnUserResponseModel.toModel(club));
		}

		return UserResponseModel.builder()
				.id(user.getId())
				.email(user.getEmail())
				.username(user.getUsername())
				.levelOfEnglish(user.getLevelOfEnglish())
				.country(user.getCountry())
				.creatingClubsByUser(creatingClubsByUserList)
				.existClubs(existClubList)
				.themesType(user.getThemes())
				.build();
	}
}
