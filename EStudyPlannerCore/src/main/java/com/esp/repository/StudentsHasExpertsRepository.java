package com.esp.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esp.model.StudentsHasExperts;

public interface StudentsHasExpertsRepository extends JpaRepository<StudentsHasExperts,String>{
	
	public ArrayList<StudentsHasExperts> findByExpertsUserName(String expertsUserName);
	public ArrayList<StudentsHasExperts> findByStudentsUserName(String studentsUserName);

}
