package com.backend.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.model.GameModel;



@Repository
public interface GameRepository extends JpaRepository<GameModel, Integer>{
	
	//GameModel findByparticipant(String username);
	
	
	public Optional<GameModel> findByGameId(Long gameId);
	

	
}
