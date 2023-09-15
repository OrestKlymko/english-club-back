package com.example.englishclub.user.repository;

import com.example.englishclub.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
	Optional<UserEntity> findUserEntityByEmail(String email);
	Optional<UserEntity> findUserEntityByUsername(String username);
}
