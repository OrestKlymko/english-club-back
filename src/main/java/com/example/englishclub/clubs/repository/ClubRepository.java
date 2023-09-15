package com.example.englishclub.clubs.repository;

import com.example.englishclub.clubs.entity.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<ClubEntity,Long> {
}
