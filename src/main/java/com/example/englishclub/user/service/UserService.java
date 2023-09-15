package com.example.englishclub.user.service;

import com.example.englishclub.clubs.entity.ClubEntity;
import com.example.englishclub.clubs.exception.CourseNotFoundException;
import com.example.englishclub.clubs.repository.ClubRepository;
import com.example.englishclub.user.entity.RoleEntity;
import com.example.englishclub.user.entity.UserEntity;
import com.example.englishclub.user.entity.enums.LevelEnglish;
import com.example.englishclub.user.entity.enums.ThemesType;
import com.example.englishclub.user.exception.IncorrectEmailException;
import com.example.englishclub.user.exception.IncorrectPasswordException;
import com.example.englishclub.user.exception.UserAlreadyExistException;
import com.example.englishclub.user.exception.UserNotFoundException;
import com.example.englishclub.user.model.UserChangePasswordModel;
import com.example.englishclub.user.model.UserLoginModel;
import com.example.englishclub.user.model.UserRegistrationModel;
import com.example.englishclub.user.model.UserResponseModel;
import com.example.englishclub.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ClubRepository clubRepository;


	public UserEntity getUserById(long id) throws UserNotFoundException {
		Optional<UserEntity> findUser = userRepository.findById(id);
		if (findUser.isPresent()) {
			return findUser.get();
		} else {
			throw new UserNotFoundException("User with id " + id + " not found");
		}
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
				RoleEntity roleUser = RoleEntity.builder()
						.name("ROLE_USER")
						.build();
				Set<RoleEntity> roles = new HashSet<>();
				roles.add(roleUser);

				UserEntity userToSave = UserEntity.builder()
						.email(model.getEmail())
						.password(model.getPasswords())
						.themes(ThemesType.valueOf(ThemesType.class, model.getThemes().getValue()))
						.levelOfEnglish(LevelEnglish.valueOf(LevelEnglish.class, model.getLevelOfEnglish().getValue()))
						.country(model.getCountry())
						.username(model.getUsername())
						.role(roles)
						.build();
				return userRepository.save(userToSave);
			}
		}
		throw new UserAlreadyExistException("User with email " + userEntityByEmail.get().getEmail() + " already exist");
	}



	private boolean createNewCustomerSuccess(String email, String password) throws IncorrectEmailException, IncorrectPasswordException {
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


	public void login(UserLoginModel userLoginModel) throws UserNotFoundException, IncorrectPasswordException {
		Optional<UserEntity> userEntityByEmail = userRepository.findUserEntityByEmail(userLoginModel.getEmail());
		if (userEntityByEmail.isPresent()) {
			if (userEntityByEmail.get().getPassword().equals(userLoginModel.getPassword())) {
				return;
			} else {
				throw new IncorrectPasswordException("Password incorrect");
			}
		}
		throw new UserNotFoundException("User with email " + userLoginModel.getEmail() + " not found");
	}

	public void changePassword(UserChangePasswordModel user) throws UserNotFoundException, IncorrectEmailException, IncorrectPasswordException {
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

	public void deleteUser(long id) throws UserNotFoundException {
		Optional<UserEntity> userEntityByEmail = userRepository.findById(id);
		if (userEntityByEmail.isEmpty()) {
			throw new UserNotFoundException("User with id " + id + " not found");
		} else {
			userRepository.deleteById(id);
		}
	}

	public void updateEnglishLevelById(long id, String newLanguage) throws UserNotFoundException {
		Optional<UserEntity> userEntityByEmail = userRepository.findById(id);
		if (userEntityByEmail.isEmpty()) {
			throw new UserNotFoundException("User with id " + id + " not found");
		} else {
			UserEntity userEntity = userEntityByEmail.get();
			userEntity.setLevelOfEnglish(LevelEnglish.valueOf(newLanguage));
			userRepository.save(userEntity);
		}
	}


	public void joinToCourse(long club_id, long user_id) throws UserNotFoundException, CourseNotFoundException {
		Optional<UserEntity> userById = userRepository.findById(user_id);
		Optional<ClubEntity> clubById = clubRepository.findById(club_id);

		if (userById.isPresent() && clubById.isPresent()) {
			UserEntity userEntity = userById.get();
			ClubEntity clubEntity = clubById.get();

			Set<ClubEntity> existClubs = userEntity.getExistClubs();
			existClubs.add(clubEntity);
			userEntity.setExistClubs(existClubs);
			userRepository.save(userEntity);
		} else if (userById.isEmpty()) {
			throw new UserNotFoundException("User with id " + user_id + " not found");
		} else {
			throw new CourseNotFoundException("Course with id " + club_id + " not found");
		}
	}

	public Optional<UserEntity> findByUserName(String username) {
		return userRepository.findUserEntityByUsername(username);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userFind = findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));

		return new User(
				userFind.getUsername(),
				userFind.getPassword(),
				userFind.getRole().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
		);
	}
}
