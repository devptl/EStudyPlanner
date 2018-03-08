package com.esp.service;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.dto.DtoOperation;
import com.esp.model.Courses;
import com.esp.model.StudentsHasCourses;

@Service
public class CoursesService {

	@Autowired
	private DtoOperation dtoOperation;

	@PersistenceContext
	EntityManager entityManager;

	/**
	 * List of all the Courses
	 * 
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> showCourses() {
		ArrayList<Courses> s1;
		s1 = dtoOperation.getCoursesComponents().allCourses();
		return s1;
	}

	/**
	 * List of all the fields
	 * 
	 * @return {@link Courses}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Courses> fieldCourses() {
		ArrayList<Courses> s1 = null;

		// To get the card type for a particular bank id
		// Native SQL Query
		String queryString = "select id_course,course_name,course_duration,parent_course_id from courses"
				+ " where parent_course_id = 0";
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, Courses.class);
		// Map result set to list of Objects
		s1 = (ArrayList<Courses>) query.getResultList();

		return s1;
	}

	/**
	 * List of all the major courses
	 * 
	 * @return {@link Courses}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Courses> mainCourses() {
		ArrayList<Courses> s1 = null;

		// To get the card type for a particular bank id
		// Native SQL Query
		String queryString = "select id_course,course_name,course_duration,parent_course_id from courses "
				+ "where parent_course_id > 0 and parent_course_id < 10 ";
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, Courses.class);
		// Map result set to list of Objects
		s1 = (ArrayList<Courses>) query.getResultList();

		return s1;
	}

	/**
	 * List of course according to given parent id
	 * 
	 * @param id
	 * @return {@link Courses}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Courses> mainCoursesById(int id) {
		ArrayList<Courses> s1 = null;

		// To get the card type for a particular bank id
		// Native SQL Query
		String queryString = "select id_course,course_name,course_duration,parent_course_id from courses "
				+ "where parent_course_id = " + id;
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, Courses.class);
		// Map result set to list of Objects
		s1 = (ArrayList<Courses>) query.getResultList();

		return s1;
	}

	/**
	 * To save the user with the courses in join table
	 * 
	 * @param s
	 */
	public void saveUserAndMainCourse(StudentsHasCourses s) {
		dtoOperation.getStudentsComponents().saveStudentsHasCourses(s);
	}

}
