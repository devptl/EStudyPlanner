package com.esp.service;

import java.util.ArrayList;

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
	 * To get the field courses
	 * 
	 * @param parentCourseId
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> getfieldCourses(int parentCourseId) {
		return dtoOperation.getCoursesComponents().getfieldCourses(parentCourseId);
	}

	/**
	 * List of all the major courses
	 * 
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> mainCourses() {

		return dtoOperation.getCoursesComponents().mainCourses();
	}

	/**
	 * List of course according to given parent id
	 * and if study material available
	 * 
	 * @param id
	 *            - ParentID
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> mainCoursesById(int id) {

		return dtoOperation.getCoursesComponents().mainCoursesById(id);
	}
	/**
	 * List of course by parent Id
	 * @param id
	 * @return
	 */
	public ArrayList<Courses> minorCoursesById(int id) {

		return dtoOperation.getCoursesComponents().minorCoursesById(id);
	}

	/**
	 * To get the courses with particualarr id
	 * 
	 * @param id
	 *            - CourseID
	 * @return {@link Courses}
	 */
	public Courses getCourseWithId(int id) {
		return dtoOperation.getCoursesComponents().findOneCourse(id);
	}
	
	

	/**
	 * To find the courses by name
	 * 
	 * @param name
	 * @return {@link Courses}
	 */
	public Courses findCourseByName(String name) {

		return dtoOperation.getCoursesComponents().findCourseByName(name);

	}

	/**
	 * Get the Courses list with particular student has registered
	 * 
	 * @param userName
	 *            - {@link Students}
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> getCoursesForStudent(String userName) {

		return dtoOperation.getCoursesComponents().getCoursesForStudent(userName);

	}

	/**
	 * Get the Courses list with particular expert has registered
	 * 
	 * @param userName
	 *            - {@link Experts}
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> getCoursesForExpert(String userName) {

		return dtoOperation.getCoursesComponents().getCoursesForExpert(userName);

	}

}
