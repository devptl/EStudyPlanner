package com.esp.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esp.model.Courses;

@Repository
public interface CoursesRepository  extends JpaRepository<Courses,Integer>{
	
   public ArrayList<Courses> findByParentCourseId(int parentCourseId);  
   public Courses findByCourseName(String courseName);
  

}
