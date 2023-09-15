package com.example.englishclub.clubs.model;

import com.example.englishclub.clubs.entity.ClubEntity;
import com.example.englishclub.user.entity.enums.LevelEnglish;
import com.example.englishclub.user.entity.enums.ThemesType;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
public class ClubOnUserResponseModel {
	private long id;
	private Date currentDate;
	private Time timeStart;
	private Time timeEnd;
	private String clubDescription;
	private ThemesType themesType;
	private LevelEnglish levelEnglish;

	public static ClubOnUserResponseModel toModel(ClubEntity clubEntity){
		return ClubOnUserResponseModel.builder()
				.id(clubEntity.getId())
				.currentDate(clubEntity.getCurrentDate())
				.timeEnd(clubEntity.getTimeEnd())
				.timeStart(clubEntity.getTimeStart())
				.clubDescription(clubEntity.getClubDescription())
				.themesType(clubEntity.getThemesType())
				.levelEnglish(clubEntity.getLevelEnglish())
				.build();
	}
}
