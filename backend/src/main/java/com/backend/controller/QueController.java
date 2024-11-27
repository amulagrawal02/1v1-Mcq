package com.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.QueModel;
import com.backend.service.QueService;

import java.util.List;

@RestController
@RequestMapping("/que")
public class QueController {
	
	private final QueService queService;
	
	@Autowired
	QueController(QueService queService)
	{
		this.queService = queService;
	}
	
	
	@PostMapping("/add-que")
	public ResponseEntity<String> addQue(@RequestBody QueModel que)
	{
		 try {
			 System.out.println(que.getUsername());
			 queService.addQuestion(que);
			 return new ResponseEntity<>("Question added successfully", HttpStatus.OK);
			 
		 }catch(Exception e){
			 return new ResponseEntity<>("problem while adding question" + e, HttpStatus.NO_CONTENT);
		 }
	}
	
	@GetMapping("/fetch/{level}")
	public ResponseEntity<List<QueModel>> fetchQue(@PathVariable  String level)
	{
		System.out.println("inside the fetch: " + level);
		
		try {
			List<QueModel> lq = queService.fetch(level);
			if(lq.size() == 0)
			{
				System.out.println("NO result found");
				
			}
			else {
				for(int i = 0; i< lq.size(); i++)
				{
					System.out.println(lq.get(i));
				}
			}
			return new ResponseEntity<>(lq, HttpStatus.OK);
			
			
		}catch (Exception e)
		{
			System.out.println("Inside the catch for fetch question");
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		
	}
	
}
