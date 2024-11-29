package com.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.backend.model.GameModel;
import com.backend.repository.GameRepository;

@Service
public class GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	
	public GameModel createRoom(GameModel gamemodel)
	{
		return gameRepository.save(gamemodel);
	}
	
	
	
}
