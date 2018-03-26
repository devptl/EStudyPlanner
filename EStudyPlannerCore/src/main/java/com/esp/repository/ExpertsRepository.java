package com.esp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esp.model.Experts;

@Repository
public interface ExpertsRepository extends JpaRepository<Experts,String>{
	
	public Experts findByEmail(String email);

}
