package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.Component.CoursesComponents;
import com.esp.model.Courses;

/**
 * Service class to provide service related to the courses
 * showCourses()                                -  List of all the Courses
 * getfieldCourses()                            -  To get the field courses
 * mainCourses()                                -  List of all the major courses
 * mainCoursesById()                            -  List by parent id and if study material available  
 * minorCoursesById()                           -  List of course by parent Id
 * getCourseWithId()                            -  To get the courses with particualarr id
 * findCourseByName()                           -  To find the courses by name
 * getCoursesForStudent()                       -  Get the Courses list with particular student has registered
 * getCoursesForExpert()                        -  Get the Courses list with particular expert has registered
 * 
 * @author mindfire
 *
 */
@Service
public class CoursesService {

	@Autowired
	private CoursesComponents coursesComponents;

	/**
	 * List of all the Courses
	 * 
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> showCourses() {
		ArrayList<Courses> s1;
		s1 = coursesComponents.allCourses();
		return s1;
	}

	/**
	 * To get the field courses
	 * 
	 * @param parentCourseId
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> getfieldCourses(int parentCourseId) {
		return coursesComponents.getfieldCourses(parentCourseId);
	}

	/**
	 * List of all the major courses
	 * 
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> mainCourses() {

		return coursesComponents.mainCourses();
	}

	/**
	 * List of course according to given parent id and if study material available
	 * 
	 * @param id
	 *            - ParentID
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> mainCoursesById(int id) {

		return coursesComponents.mainCoursesById(id);
	}

	/**
	 * List of course by parent Id
	 * 
	 * @param id
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> minorCoursesById(int id) {

		return coursesComponents.minorCoursesById(id);
	}

	/**
	 * To get the courses with particualarr id
	 * 
	 * @param id
	 *            - CourseID
	 * @return {@link Courses}
	 */
	public Courses getCourseWithId(int id) {
		return coursesComponents.findOneCourse(id);
	}

	/**
	 * To find the courses by name
	 * 
	 * @param name
	 *            - courseName
	 * @return {@link Courses}
	 */
	public Courses findCourseByName(String name) {
		return coursesComponents.findCourseByName(name);

	}

	/**
	 * Get the Courses list with particular student has registered
	 * 
	 * @param userName
	 *            - student username
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> getCoursesForStudent(String userName) {

		return coursesComponents.getCoursesForStudent(userName);

	}

	/**
	 * Get the Courses list with particular expert has registered
	 * 
	 * @param userName
	 *            - experts Username
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> getCoursesForExpert(String userName) {

		return coursesComponents.getCoursesForExpert(userName);

	}

}
