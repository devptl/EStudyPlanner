package com.esp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esp.model.StudentsHasCourses;

public interface StudentsHasCoursesRepository extends JpaRepository<StudentsHasCourses,String>{

}
