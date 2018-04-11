package com.esp.Component;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esp.model.Students;
import com.esp.model.StudentsHasCourses;
import com.esp.model.StudentsHasExperts;
import com.esp.model.StudentsHasStudyMaterials;
import com.esp.repository.StudentsHasCoursesRepository;
import com.esp.repository.StudentsHasExpertsRepository;
import com.esp.repository.StudentsHasStudyMaterialsRepository;
import com.esp.repository.StudentsRepository;

/**
 * Component class to provide components on students
 * allStudents()                                 -  Return list of all Students
 * findOneStudent()                              -  Return a particular Student
 * saveStudent()                                 -  To save a particular Student in database
 * saveStudentsHasCourses()                      -  To save in student has Courses tables
 * saveStudentsHasExperts()                      -  To save in student has Experts tables
 * saveStudentsHasStudyMaterials()               -  To save the student has Study Material
 * deleteStudentsHasStudyMaterials()             -  To delete row from student has study material
 * 
 * @author mindfire
 *
 */
@Component
public class StudentsComponents {

	@Autowired
	private StudentsRepository studentsRepository;

	@Autowired
	private StudentsHasCoursesRepository studentHasCoursesRepository;

	@Autowired
	private StudentsHasExpertsRepository studentsHasExpertsRepository;

	@Autowired
	private StudentsHasStudyMaterialsRepository studentsHasStudyMaterialsRepository;

	/**
	 * return list of all Students
	 * 
	 * @return {@link Students}
	 */
	public ArrayList<Students> allStudents() {
		return (ArrayList<Students>) studentsRepository.findAll();
	}

	/**
	 * return a particular Student
	 * 
	 * @param userName
	 * @return {@link Students}
	 */
	public Students findOneStudent(String userName) {
		return studentsRepository.findOne(userName);
	}

	/**
	 * To save a particular Student in database
	 * 
	 * @param Students
	 */
	public void saveStudent(Students s) {
		studentsRepository.save(s);
	}

	/**
	 * To save in student has Courses tables
	 * 
	 * @param StudentsHasCourses
	 */
	public void saveStudentsHasCourses(StudentsHasCourses sh) {
		studentHasCoursesRepository.save(sh);
	}

	/**
	 * To save in student has Experts tables
	 * 
	 * @param StudentsHasExperts
	 */
	public void saveStudentsHasExperts(StudentsHasExperts sh) {
		studentsHasExpertsRepository.save(sh);
	}

	/**
	 * To save the student has Study Material
	 * 
	 * @param StudentsHasStudyMaterials
	 */
	public void saveStudentsHasStudyMaterials(StudentsHasStudyMaterials sh) {
		studentsHasStudyMaterialsRepository.save(sh);
	}

	/**
	 * To delete row from student has study material
	 * 
	 * @param StudentsHasStudyMaterials
	 */
	public void deleteStudentsHasStudyMaterials(StudentsHasStudyMaterials sh) {
		studentsHasStudyMaterialsRepository.delete(sh);
	}

}
