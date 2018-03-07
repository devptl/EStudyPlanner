package com.esp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esp.model.Courses;

@Repository
public interface CoursesRepository  extends JpaRepository<Courses,Integer>{

}
