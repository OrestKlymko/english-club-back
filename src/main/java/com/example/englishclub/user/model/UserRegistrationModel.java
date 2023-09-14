package com.example.englishclub.user.model;

import com.example.englishclub.user.entity.enums.LevelEnglish;
import com.example.englishclub.user.entity.enums.ThemesType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegistrationModel {
	private String email;
	private String username;
	private String passwords;
	private ThemesType themes;
	private String country;
	private LevelEnglish levelOfEnglish;
}
