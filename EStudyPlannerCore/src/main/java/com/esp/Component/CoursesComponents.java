package com.esp.Component;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esp.model.Courses;
import com.esp.model.ExpertsHasCourses;
import com.esp.model.StudentsHasCourses;
import com.esp.repository.CoursesRepository;
import com.esp.repository.ExpertsHasCoursesRepository;
import com.esp.repository.StudentsHasCoursesRepository;
import com.esp.repository.StudyMaterialsRepository;

@Component
public class CoursesComponents {

	@Autowired
	private CoursesRepository coursesRepository;

	@Autowired
	private StudyMaterialsRepository studyMaterialsRepository;

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
	 *            - CourseId
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
	 * @return {@link Courses}
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

		for (int i = 1; i < 10; i++) {
			s1.addAll(coursesRepository.findByParentCourseId(i));
		}

		return s1;
	}

	public ArrayList<Courses> minorCoursesById(int id) {
		return coursesRepository.findByParentCourseId(id);
	}

	/**
	 * List of course according to given parent id
	 * 
	 * @param id
	 *            - CourseId
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> mainCoursesById(int id) {
		ArrayList<Courses> mainList = coursesRepository.findByParentCourseId(id);
		ArrayList<Courses> myList = new ArrayList<>();

		// getting the courses that has the study materials
		for (int i = 0; i < mainList.size(); i++) {
			int couseId = mainList.get(i).getIdCourse();
			if (studyMaterialsRepository.findByCoursesIdCourse(couseId).size() != 0) {
				// adding that to the list
				myList.add(mainList.get(i));
			}

		}
		return myList;
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
	 *            - Students UserName
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> getCoursesForStudent(String userName) {
		ArrayList<Courses> myList = new ArrayList<>();
		ArrayList<StudentsHasCourses> mainList = studentHasCoursesRepository.findByStudentsUserName(userName);

		// getting the couses that is associated to particular student
		for (int i = 0; i < mainList.size(); i++) {
			Courses c = coursesRepository.findOne(mainList.get(i).getCoursesIdCourse());
			// adding it to the list
			myList.add(c);
		}

		return myList;
	}

	/**
	 * Get the Courses list with particular expert has registered
	 * 
	 * @param userName
	 *            - Experts UserName
	 * @return {@link Courses}
	 */
	public ArrayList<Courses> getCoursesForExpert(String userName) {
		ArrayList<Courses> myList = new ArrayList<>();
		ArrayList<ExpertsHasCourses> mainList = expertsHasCoursesRepository.findByExpertsUserName(userName);

		// getting the courses associated to the particular expert
		for (int i = 0; i < mainList.size(); i++) {
			Courses c = coursesRepository.findOne(mainList.get(i).getCoursesIdCourse());
			// adding it to the list
			myList.add(c);
		}

		return myList;
	}

}
