package com.example.englishclub.entity;


import com.example.englishclub.entity.enums.LevelEnglish;
import com.example.englishclub.entity.enums.ThemesType;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@Column(name = "email")
	private String email;
	@Enumerated(EnumType.STRING)
	@Column(name = "themes")
	private ThemesType themes;
	@Column(name = "country")
	private String country;
	@Column(name = "level_of_english")
	private LevelEnglish levelOfEnglish;
}
