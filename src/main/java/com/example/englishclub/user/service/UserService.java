package com.example.englishclub.user.service;

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
import com.example.englishclub.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public UserEntity getUserById(long id) throws UserNotFoundException {
		Optional<UserEntity> findUser = userRepository.findById(id);
		if (findUser.isPresent()) {
			return findUser.get();
		} else {
			throw new UserNotFoundException("User with id " + id + " not found");
		}
	}

	public List<UserEntity> getAll() {
		return userRepository.findAll();
	}

	public UserEntity registerNewCustomer(UserRegistrationModel model)
			throws UserAlreadyExistException, IncorrectEmailException, IncorrectPasswordException {
		Optional<UserEntity> userEntityByEmail = userRepository.findUserEntityByEmail(model.getEmail());
		if (userEntityByEmail.isEmpty()) {
			if (createNewCustomerSuccess(model.getEmail(), model.getPasswords())) {
				UserEntity userToSave = UserEntity.builder()
						.email(model.getEmail())
						.password(model.getPasswords())
						.themes(ThemesType.valueOf(ThemesType.class, model.getThemes().getValue()))
						.levelOfEnglish(LevelEnglish.valueOf(LevelEnglish.class, model.getLevelOfEnglish().getValue()))
						.country(model.getCountry())
						.username(model.getUsername())
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
}
