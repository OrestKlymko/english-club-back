package com.example.englishclub.user.service;

import com.example.englishclub.clubs.entity.ClubEntity;
import com.example.englishclub.clubs.exception.CourseNotFoundException;
import com.example.englishclub.clubs.repository.ClubRepository;
import com.example.englishclub.security.SecurityConfig;
import com.example.englishclub.security.exception.UserNotAuthenticated;
import com.example.englishclub.security.jwt.JWTtoken;
import com.example.englishclub.security.jwt.JwtRequest;
import com.example.englishclub.user.entity.UserEntity;
import com.example.englishclub.user.entity.enums.LevelEnglish;
import com.example.englishclub.user.entity.enums.ThemesType;
import com.example.englishclub.user.exception.IncorrectEmailException;
import com.example.englishclub.user.exception.IncorrectPasswordException;
import com.example.englishclub.user.exception.UserAlreadyExistException;
import com.example.englishclub.user.exception.UserNotFoundException;
import com.example.englishclub.user.model.UserChangePasswordModel;
import com.example.englishclub.user.model.UserRegistrationModel;
import com.example.englishclub.user.model.UserResponseModel;
import com.example.englishclub.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private SecurityConfig securityConfig;

	@Autowired
	private JWTtoken jwTtoken;


	public UserEntity getUser() throws UserNotFoundException, UserNotAuthenticated {

		return getAuthenticatedUser();
	}

	public List<UserResponseModel> getAll() {
		List<UserEntity> all = userRepository.findAll();
		List<UserResponseModel> userList = new LinkedList<>();
		for (UserEntity userEntity : all) {
			userList.add(UserResponseModel.toModel(userEntity));
		}
		return userList;
	}

	public UserEntity registerNewCustomer(UserRegistrationModel model)
			throws UserAlreadyExistException, IncorrectEmailException, IncorrectPasswordException {
		Optional<UserEntity> userEntityByEmail = userRepository.findUserEntityByEmail(model.getEmail());
		if (userEntityByEmail.isEmpty()) {
			if (createNewCustomerSuccess(model.getEmail(), model.getPasswords())) {


				UserEntity userToSave = UserEntity.builder()
						.email(model.getEmail())
						.password(securityConfig.passwordEncoder().encode(model.getPasswords()))
						.themes(ThemesType.valueOf(ThemesType.class, model.getThemes().getValue()))
						.levelOfEnglish(LevelEnglish.valueOf(LevelEnglish.class, model.getLevelOfEnglish().getValue()))
						.country(model.getCountry())
						.username(model.getUsername())
						.role("ROLE_USER")
						.enabled(true)
						.build();
				return userRepository.save(userToSave);
			}
		}
		throw new UserAlreadyExistException("User with email " + userEntityByEmail.get().getEmail() + " already exist");
	}


	private boolean createNewCustomerSuccess(String email, String password) throws
			IncorrectEmailException, IncorrectPasswordException {
		String validEmailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
		String validPassword = "^(?=.*[0-9])(?=.*[a-z]).{8,20}$";
		if (!email.matches(validEmailRegex)) {
			throw new IncorrectEmailException("Incorrect email");
		}
		if (!password.matches(validPassword)) {
			throw new IncorrectPasswordException("Incorrect password. Password should contain 1 digit, 1 capital and 1 lowercase char, min length 8 and max 20");
		}
		return true;
	}


	public String loginUser(JwtRequest jwtRequest) throws Exception {
		Authentication authentication = securityConfig.authenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword())
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return jwTtoken.generateToken(userDetails);
	}


	public void changePassword(UserChangePasswordModel user) throws
			UserNotFoundException, IncorrectEmailException, IncorrectPasswordException {
		Optional<UserEntity> userEntityByEmail = userRepository.findUserEntityByEmail(user.getEmail());
		if (userEntityByEmail.isEmpty()) {
			throw new UserNotFoundException("User with email " + user.getEmail() + " not found");
		} else {
			UserEntity userEntity = userEntityByEmail.get();
			if (createNewCustomerSuccess(user.getEmail(), user.getNewPassword())) {
				userEntity.setPassword(user.getNewPassword());
				userRepository.save(userEntity);
			}
		}
	}

	public void deleteUser() throws UserNotFoundException {
		UserEntity correctAuth = getAuthenticatedUser();
		userRepository.deleteById(correctAuth.getId());

	}

	public void updateEnglishLevelById(String newLanguage) throws UserNotFoundException {
		UserEntity authenticatedUser = getAuthenticatedUser();
		authenticatedUser.setLevelOfEnglish(LevelEnglish.valueOf(newLanguage));
		userRepository.save(authenticatedUser);
	}


	public void joinToCourse(long club_id) throws UserNotFoundException, CourseNotFoundException {
		UserEntity authenticatedUser = getAuthenticatedUser();
		Optional<ClubEntity> clubById = clubRepository.findById(club_id);
		if (clubById.isPresent()) {
			ClubEntity clubEntity = clubById.get();
			Set<ClubEntity> existClubs = authenticatedUser.getExistClubs();
			existClubs.add(clubEntity);
			authenticatedUser.setExistClubs(existClubs);
			userRepository.save(authenticatedUser);
		} else {
			throw new CourseNotFoundException("Course with id " + club_id + " not found");
		}
	}

	private UserEntity getAuthenticatedUser() throws UserNotFoundException {

		Authentication auth = securityConfig.getAuth();
		if (auth != null && auth.isAuthenticated()) {
			Optional<UserEntity> userByUsername = userRepository.findUserEntityByUsername(auth.getName());
			if (userByUsername.isPresent()) {
				return userByUsername.get();
			}
		}
		throw new UserNotFoundException("User  not found");
	}
}
