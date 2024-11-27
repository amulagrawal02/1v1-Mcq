package com.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.backend.model.ParticipantModel;
import com.backend.repository.ParticipantRepository;

@Service
public class ParticipantService {

	@Autowired
	public ParticipantRepository participantRepository;
	
	public void addParticipant(ParticipantModel participantmodel)
	{
		
		participantRepository.save(participantmodel);
	}
	
	public List<ParticipantModel> findParticipantwithGivenGameId(Long game_id)
	{
//		
		List<ParticipantModel> ps =  participantRepository.findByGameId(game_id);
		

		return ps;
	}
	
}
