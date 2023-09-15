package com.example.englishclub.clubs.entity;

import com.example.englishclub.user.entity.UserEntity;
import com.example.englishclub.user.entity.enums.LevelEnglish;
import com.example.englishclub.user.entity.enums.ThemesType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clubs")
public class ClubEntity {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "current_date_creation")
	private Date currentDate;
	@Column(name = "time_start")
	@Temporal(TemporalType.TIME)
	private Time timeStart;
	@Column(name = "time_end")
	@Temporal(TemporalType.TIME)
	private Time timeEnd;
	@Column(name = "club_descriptions")
	private String clubDescription;
	@Enumerated(EnumType.STRING)
	@Column(name = "themes")
	private ThemesType themesType;
	@Enumerated(EnumType.STRING)
	@Column(name = "level_of_english")
	private LevelEnglish levelEnglish;
	@ManyToMany(mappedBy = "creationClubs")
	@JsonIgnoreProperties("creationClubs")
	private Set<UserEntity> userCreation;
	@ManyToMany(mappedBy = "existClubs")
	@JsonIgnoreProperties("existClubs")
	private Set<UserEntity> userExists;
}
