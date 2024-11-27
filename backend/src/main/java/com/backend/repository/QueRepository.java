package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.backend.model.QueModel;


@Component
@Repository
public interface QueRepository extends JpaRepository<QueModel, Integer> {
	
	List<QueModel> findAllBylevel(String username);

}
