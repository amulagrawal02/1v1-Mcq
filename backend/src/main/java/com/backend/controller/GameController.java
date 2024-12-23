package com.backend.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import java.util.ArrayList;
import com.backend.jwt.JwtHelper;
import com.backend.model.GameModel;
import com.backend.service.GameService;
import com.backend.service.ParticipantService;
import com.backend.service.UserService;
import com.backend.model.ParticipantModel;

@RestController
@RequestMapping("/game")
public class GameController {

	
	private final GameService gameService;
	private final ParticipantService participantService;
	private final UserService userService;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private JwtHelper jwthelper;
	
	

	@Autowired
	GameController(GameService gameService, ParticipantService participantService,UserService userService)
	{
		this.gameService = gameService;
		this.participantService = participantService;
		this.userService = userService;
	}
	
	
	@PostMapping("/create-room")
	public ResponseEntity<String> createRoom(@RequestBody GameModel gamemodel)
	{

		//String loggedInUser = jwthelper.getUsernameFromToken();
		System.out.println(gamemodel + "before creating a room");
		gamemodel = gameService.createRoom(gamemodel);
		
		
		if(gamemodel.getGameId() != null)
		{
			System.out.println("owner is added");
			ParticipantModel participant = new ParticipantModel(gamemodel.getGameId(), gamemodel.getOwner());
			
			
			try {
				participantService.addParticipant(participant);
				String res = Long.toString(gamemodel.getGameId());
				return new ResponseEntity<>(res, HttpStatus.OK);
			}catch(Exception e)
			{
				System.out.println("Error while adding in participant_model table" + e);	
				return new ResponseEntity<>("Error while adding in participant_model table ", HttpStatus.BAD_REQUEST);
				
			}
		}
		else {
			return new ResponseEntity<>("Error while creating a game room", HttpStatus.OK);
		}
	}
	
	@PostMapping("/join-room")
	public ResponseEntity<String> joinRoom(@RequestParam("gameId") String gameId , @RequestHeader(HttpHeaders.AUTHORIZATION) String authtoken)
	{
		Long roomID = Long.parseLong(gameId); 
		String token = authtoken.substring(7);
		String user = jwthelper.getUsernameFromToken(token);
		ParticipantModel participant = new ParticipantModel(roomID, user);
		
		System.out.println("username while user try to Join the room: " + user);
		
		Optional<ParticipantModel> ps = participantService.findByGameIdAndParticipant(roomID, user);
		if(ps.isPresent())
		{	
			System.out.println("User Already joined the group" + user);	
			return new ResponseEntity<>("User Already joined the group", HttpStatus.CONFLICT);
		}
		
		try {
			
			
			Optional<GameModel> gm = gameService.findByGameId(roomID);
			
			if(gm.isPresent())
			{
				System.out.println("Gamd Id find");
				GameModel game = gm.get();
				
				if(game.getIsBlocked())
				{
					// status code : 509
					return new ResponseEntity<>("Lobby is Blocked new user can't Login in it", HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
				}
				else {
					participantService.addParticipant(participant);
					System.out.println("Add the participant in the room: " + gameId + user);
					
					return new ResponseEntity<>("added in the room with room no :", HttpStatus.OK);
				}
			}
			
			else {
				System.out.println("Wrong Lobby ID");
				// 405
				 return new ResponseEntity<>("In Correct Lobby Id", HttpStatus.METHOD_NOT_ALLOWED);
			}
			
		}catch(Exception e)
		{
			System.out.println("Error while adding participant: " + user + ", Error: " + e.getMessage());
			// status code 500
	        return new ResponseEntity<>("Error while adding participant", HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
	
	// live update user 
	@MessageMapping("/play/gameid/{id}")
	@SendTo("/play/gameid/{id}")

	public List<ParticipantModel> LiveUpdateOfJoinParticipant(@DestinationVariable("id") String id )
	{
		
	
		System.out.println("game id for getting live updates: "+  id);
		Long roomID = Long.parseLong(id); 
		
		try {
			List<ParticipantModel> ps =  participantService.findParticipantwithGivenGameId(roomID);
			System.out.println(ps);
			
			return ps;
		}catch(Exception e)
		{
			System.out.println("getting error while find the participantwithgivemGameId" + e);
			return null;
		}
			
	}
	@PostMapping("/play/gameid/changestatus/{id}")
	public void LiveStatusChange(@PathVariable("id") String id)
	{
		System.out.println("gameid + username + status: " + id);
		String[] details = id.split("\\$");

		
		int status = 0;
		
		if(details[2].contains("1"))
		{
			status = 1;
		}
		else {
			status = 0;
		}
		long roomID = Long.parseLong(details[0]);
		Optional<ParticipantModel> ps = participantService.findByGameIdAndParticipant(roomID, details[1]);
		
		if(ps.isPresent()) {
			System.out.println("get the participant detials with the gameID:" + roomID +" and username " + details[1]);
			
			ParticipantModel prt = ps.get();
			System.out.println(prt);
			if(status == 1)
			{
				prt.setStatus(status);
				participantService.addParticipant(prt);
			}
			else {
				participantService.deleteParticipant(prt);
			}	
			
			List<ParticipantModel> updatedParticipants = participantService.findParticipantwithGivenGameId(roomID);
			messagingTemplate.convertAndSend("/play/gameid/" + roomID, updatedParticipants);
			
			//return updatedParticipants; 
			
			
		}
		else {
			System.out.println("participant not joined the room and try to fetch the data");
			//return null;
			
		}
		
	}
	
	@PostMapping("/blockLobby/{id}")
	public ResponseEntity<String> blockLobby(@PathVariable("id") String id)
	{
		long roomID = Long.parseLong(id);
		Optional<GameModel> gm = gameService.findByGameId(roomID);
		
		if(gm.isPresent())
		{
			System.out.println("Gamd Id find");
			GameModel game = gm.get();
			
			System.out.println("game ID finded");
			game.setIsBlocked(true);
			gameService.createRoom(game);
			
			 return new ResponseEntity<>("Error while adding participant", HttpStatus.OK);
			
		}
		
		else {
			System.out.println("FAcing problem while blocking the Lobby");
			 return new ResponseEntity<>("Error while adding participant", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
