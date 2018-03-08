package com.esp.Component;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.model.Courses;
import com.esp.repository.CoursesRepository;

@Service
public class CoursesComponents {

	@Autowired
	private CoursesRepository coursesRepository;


	/**
	 * return list of all Courses
	 * 
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> allCourses() {
		return (ArrayList<Courses>) coursesRepository.findAll();
	}

	/**
	 * return a particular Course
	 * 
	 * @param id
	 * @return {@link Courses}
	 */
	public Courses findOneCourse(int id) {
		return coursesRepository.findOne(id);
	}
	
	/**
	 * To save a particular course in database
	 * @param Courses
	 */
	public void saveCourse(Courses c) {
		coursesRepository.save(c);
	}


}
