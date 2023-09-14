package com.example.englishclub.user.service;

import com.example.englishclub.user.entity.UserEntity;
import com.example.englishclub.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public UserEntity getUserById(long id){
		return userRepository.getById(id);
	}

	public List<UserEntity> getAll(){
		return userRepository.findAll();
	}

}
