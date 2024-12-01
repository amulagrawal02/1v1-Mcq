package com.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.backend.model.GameModel;
import com.backend.model.ParticipantModel;
import com.backend.repository.ParticipantRepository;

@Service
public class ParticipantService {

	@Autowired
	public ParticipantRepository participantRepository;
	
	public void addParticipant(ParticipantModel participantmodel)
	{
		
		participantRepository.save(participantmodel);
		
		System.out.println( "particpiapant model detials inside the addParticipants:  " + participantmodel);
	}
	
	public List<ParticipantModel> findParticipantwithGivenGameId(Long game_id)
	{
//		
		List<ParticipantModel> ps =  participantRepository.findByGameId(game_id);
		

		return ps;
	}
	
	
	public ParticipantModel findByParticipant(String username)
	{
		return participantRepository.findByParticipant(username);
	}
	
	public Optional<ParticipantModel> findByGameIdAndParticipant(Long game_id, String username) {
	    return participantRepository.findByGameIdAndParticipant(game_id, username);
	}
	
	public void deleteParticipant(ParticipantModel prt)
	{
		participantRepository.delete(prt);
	}

}
