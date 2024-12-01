package com.backend.model;

import java.util.List;
import java.util.Random;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "game_model")  // Optional: Specify the table name
public class GameModel {

    @Id
    // 6 digit game id
    private Long gameId;
   

    // Owner refers to the one who creates the lobby; here owner and the username are the same
    private String owner;
    
    
    private Boolean isBlocked = false;

    
    public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	// for generating the random 6 digit no. 
    private Long generate6DigitGameId()
    {
    	Random random = new Random();
    	return 100000L + random.nextInt(900000);
    }
    
    public GameModel()
    {
    	setGameId(generate6DigitGameId());
    }
    
    
    
    // Getters and Setters

	@Override
	public String toString() {
		return "GameModel [gameId=" + gameId + ", owner=" + owner + ", isBlocked=" + isBlocked + "]";
	}

	public Long getGameId() {
        return gameId;
    }

   

	public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
