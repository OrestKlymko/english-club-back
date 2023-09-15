package com.example.englishclub.user.model;

import com.example.englishclub.user.entity.UserEntity;
import com.example.englishclub.user.entity.enums.LevelEnglish;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserResponseInClubModel {
	private long id;
	private String email;
	private String username;
	private LevelEnglish levelOfEnglish;
	private String country;

	public static UserResponseInClubModel toModel(UserEntity user){
			return UserResponseInClubModel.builder()
					.email(user.getEmail())
					.username(user.getUsername())
					.id(user.getId())
					.levelOfEnglish(user.getLevelOfEnglish())
					.country(user.getCountry())
					.build();
	}
}
