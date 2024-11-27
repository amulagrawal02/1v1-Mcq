package com.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.backend.model.QueModel;
import com.backend.repository.QueRepository;

@Component
@Service
public class QueService {

	
	@Autowired
	private QueRepository queRepository;
	
	public void addQuestion(QueModel que) {
		
		System.out.println("Quest" +  que + " inside the addQuestion function in QueService");
		queRepository.save(que);
		
	}
	
	public List<QueModel> fetch(String level){
		System.out.println("fetch the question with level of " +  level + " inside the fetch fucntion in QueSerive");
		List<QueModel> lq = queRepository.findAllBylevel(level);
	
		return lq;
	}

}
