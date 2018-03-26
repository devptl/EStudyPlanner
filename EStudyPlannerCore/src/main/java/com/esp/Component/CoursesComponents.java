package com.esp.Component;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.ExpertsHasCourses;
import com.esp.model.Students;
import com.esp.model.StudentsHasCourses;
import com.esp.repository.CoursesRepository;
import com.esp.repository.ExpertsHasCoursesRepository;
import com.esp.repository.StudentsHasCoursesRepository;

@Component
public class CoursesComponents {

	@Autowired
	private CoursesRepository coursesRepository;
	
	@Autowired
	private StudentsHasCoursesRepository studentHasCoursesRepository;
	
	@Autowired
	private ExpertsHasCoursesRepository expertsHasCoursesRepository;


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
	 * 
	 * @param Courses
	 */
	public void saveCourse(Courses c) {
		coursesRepository.save(c);
	}

	/**
	 * List of all filed courses
	 * 
	 * @param parentCourseId
	 * @return
	 */
	public ArrayList<Courses> getfieldCourses(int parentCourseId) {
		
		return coursesRepository.findByParentCourseId(parentCourseId);
	}

	/**
	 * List of all the major courses
	 * 
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> mainCourses() {
		ArrayList<Courses> s1 = new ArrayList<>();

		for (int i=1;i<10;i++)
		{
			s1.addAll(coursesRepository.findByParentCourseId(i));
		}

		return s1;
	}

	/**
	 * List of course according to given parent id
	 * 
	 * @param id
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> mainCoursesById(int id) {
		ArrayList<Courses> s1 = coursesRepository.findByParentCourseId(id);
		return s1;
	}

	/**
	 * To find the courses by name
	 * 
	 * @param name
	 * @return
	 */
	public Courses findCourseByName(String name) {

		Courses c = coursesRepository.findByCourseName(name);
		return c;

	}

	/**
	 * Get the Courses list with particular student has registered
	 * 
	 * @param userName
	 *            - {@link Students}
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> getCoursesForStudent(String userName) {
		ArrayList<Courses> myList = new ArrayList<>();

		ArrayList<StudentsHasCourses> mainList =  studentHasCoursesRepository.findByStudentsUserName(userName);
		
		for(int i=0;i<mainList.size();i++)
		{
			Courses c = coursesRepository.findOne(mainList.get(i).getCoursesIdCourse());
			myList.add(c);
		}
		
		return myList;

	}

	/**
	 * Get the Courses list with particular expert has registered
	 * 
	 * @param userName
	 *            - {@link Experts}
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> getCoursesForExpert(String userName) {
		ArrayList<Courses> myList = new ArrayList<>();

		ArrayList<ExpertsHasCourses> mainList =  expertsHasCoursesRepository.findByExpertsUserName(userName);
		
		for(int i=0;i<mainList.size();i++)
		{
			Courses c = coursesRepository.findOne(mainList.get(i).getCoursesIdCourse());
			myList.add(c);
		}
		
		return myList;


	}

}
