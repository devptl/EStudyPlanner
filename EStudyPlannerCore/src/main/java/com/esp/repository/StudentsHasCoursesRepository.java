package com.esp.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esp.model.StudentsHasCourses;

public interface StudentsHasCoursesRepository extends JpaRepository<StudentsHasCourses,String>{
	
	public ArrayList<StudentsHasCourses> findByStudentsUserName(String studentsUserName);

}
