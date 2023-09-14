package com.example.englishclub.user.entity;


import com.example.englishclub.user.entity.enums.LevelEnglish;
import com.example.englishclub.user.entity.enums.ThemesType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "users")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
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
	@Enumerated(EnumType.STRING)
	@Column(name = "level_of_english")
	private LevelEnglish levelOfEnglish;
}
