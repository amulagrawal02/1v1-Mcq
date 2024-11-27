package com.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;


@Entity

@Table(name = "participant_model")
public class ParticipantModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private int status = 0;

    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "game_id")
	private Long gameId;
    
    private String participant;
    
 // Default constructor
    public ParticipantModel() {
    }
    
    public ParticipantModel(Long game_id, String participant) {
		super();
		this.gameId = game_id;
		this.participant = participant;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getGame_id() {
		return gameId;
	}

	public void setGame_id(Long game_id) {
		this.gameId = game_id;
	}

	

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	@Override
	public String toString() {
		return "ParticipantModel [id=" + id + ", game_id=" + gameId + ", participant=" + participant + "]";
	}
    
    
    
}
