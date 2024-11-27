package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.backend.model.UserModel;

@Component
@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer>{

	UserModel findByUsername(String username);

	
	
}
