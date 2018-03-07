package com.esp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esp.model.StudentsHasCourses;

public interface StudentHasCoursesRepository extends JpaRepository<StudentsHasCourses,String>{

}
