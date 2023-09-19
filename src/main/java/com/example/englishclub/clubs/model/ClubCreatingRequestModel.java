package com.example.englishclub.clubs.model;

import com.example.englishclub.user.entity.UserEntity;
import com.example.englishclub.user.entity.enums.LevelEnglish;
import com.example.englishclub.user.entity.enums.ThemesType;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
public class ClubCreatingRequestModel {
		private String clubName;
		private Date currentDate;
		private Time timeStart;
		private Time timeEnd;
		private String clubDescription;
		private ThemesType themesType;
		private LevelEnglish levelEnglish;
	}



