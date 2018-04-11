package com.esp.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esp.model.ExpertsHasCourses;

public interface ExpertsHasCoursesRepository extends JpaRepository<ExpertsHasCourses,String>{
	
	public ArrayList<ExpertsHasCourses> findByExpertsUserName(String expertsUserName);
	public ArrayList<ExpertsHasCourses> findByCoursesIdCourse(int coursesIdCourse);
	public ExpertsHasCourses findByExpertsUserNameAndCoursesIdCourse(String expertsUserName,int coursesIdCourse);

}
