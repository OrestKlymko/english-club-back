package com.example.englishclub.clubs.model;

import com.example.englishclub.clubs.entity.ClubEntity;
import com.example.englishclub.user.entity.UserEntity;
import com.example.englishclub.user.entity.enums.LevelEnglish;
import com.example.englishclub.user.entity.enums.ThemesType;
import com.example.englishclub.user.model.UserResponseInClubModel;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class ClubResponseModel {
	private long id;
	private Date currentDate;
	private Time timeStart;
	private Time timeEnd;
	private String clubDescription;
	private ThemesType themesType;
	private LevelEnglish levelEnglish;

	private UserEntity userCreation;
	private Set<UserResponseInClubModel> userExists;

	public static ClubResponseModel toModel(ClubEntity clubEntity){
		 Set<UserResponseInClubModel> userExistSet = new HashSet<>();


		for (UserEntity userEntity : clubEntity.getUserExists()) {
			userExistSet.add(UserResponseInClubModel.toModel(userEntity));
		}

		return ClubResponseModel.builder()
				.id(clubEntity.getId())
				.currentDate(clubEntity.getCurrentDate())
				.timeStart(clubEntity.getTimeStart())
				.timeEnd(clubEntity.getTimeEnd())
				.clubDescription(clubEntity.getClubDescription())
				.themesType(clubEntity.getThemesType())
				.levelEnglish(clubEntity.getLevelEnglish())
				.userCreation(clubEntity.getUserCreation())
				.userExists(userExistSet)
				.build();

	}
}
