package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.model.GameModel;

@Repository
public interface GameRepository extends JpaRepository<GameModel, Integer>{
	
}
