

package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.model.ParticipantModel;

@Repository
public interface ParticipantRepository extends JpaRepository<ParticipantModel, Integer>{
	
	List<ParticipantModel> findByGameId(Long game_id);
	
	ParticipantModel findByParticipant(String participantName);

	
}
