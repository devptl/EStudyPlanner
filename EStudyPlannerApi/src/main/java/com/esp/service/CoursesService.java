package com.esp.service;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.dto.DtoOperation;
import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.Students;

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

		// To get the field courses list
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

		// To get the major courses list
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

		// To get the course list with particular parent id
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
	 * To get the courses with particualarr id
	 * 
	 * @param id - CourseID
	 * @return {@link Courses}
	 */
	public Courses getCourseWithId(int id) {
		return dtoOperation.getCoursesComponents().findOneCourse(id);
	}

	public Courses findCourseByName(String name) {

		Courses c = null;

		// To find the courses by name
		String queryString = "select * from courses where course_name =\"" + name + "\"";
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, Courses.class);
		// Map result set to list of Objects
		c = (Courses) query.getSingleResult();

		return c;

	}

	/**
	 * Get the Courses list with particular student has registered
	 * 
	 * @param userName - {@link Students}
	 * @return {@link Courses} 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Courses> getCoursesForStudent(String userName) {
		ArrayList<Courses> c = null;

		// To find the couses list for particular student
		String queryString = "select c.id_course,c.course_name,c.course_duration,c.parent_course_id from"
				+ " courses c inner join students_has_courses sh" + " on c.id_course=sh.courses_id_course "
				+ "and sh.students_user_name=\"" + userName + "\"";
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, Courses.class);
		// Map result set to list of Objects
		c = (ArrayList<Courses>) query.getResultList();

		return c;

	}

	/**
	 * Get the Courses list with particular expert has registered
	 * 
	 * @param userName - {@link Experts}
	 * @return {@link Courses}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Courses> getCoursesForExpert(String userName) {
		ArrayList<Courses> c = null;

		// To fint the course list for expert
		String queryString = "select c.id_course,c.course_name,c.course_duration,c.parent_course_id from"
				+ " courses c inner join experts_has_courses eh" + " on c.id_course=eh.courses_id_course "
				+ "and eh.experts_user_name=\"" + userName + "\"";
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, Courses.class);
		// Map result set to list of Objects
		c = (ArrayList<Courses>) query.getResultList();

		return c;

	}

}
