package com.example.englishclub.user.entity;


import com.example.englishclub.clubs.entity.ClubEntity;
import com.example.englishclub.user.entity.enums.LevelEnglish;
import com.example.englishclub.user.entity.enums.ThemesType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Table(name = "users")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	@Column(name = "email")
	private String email;
	@Column(name = "username")
	private String username;
	@Column(name = "passwords")
	@JsonIgnore
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(name = "themes")
	private ThemesType themes;
	@Column(name = "country")
	private String country;
	@Enumerated(EnumType.STRING)
	@Column(name = "level_of_english")
	private LevelEnglish levelOfEnglish;
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "userCreation")
	private Set<ClubEntity> creationClubs;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "exist_club_in_user",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "club_id")
	)
	private Set<ClubEntity> existClubs;
	@Column(name = "role")
	private String role;
	@Column(name = "enabled")
	@JsonIgnore
	private boolean enabled;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserEntity that = (UserEntity) o;
		return id == that.id && Objects.equals(email, that.email) && Objects.equals(username, that.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email, username);
	}
}
