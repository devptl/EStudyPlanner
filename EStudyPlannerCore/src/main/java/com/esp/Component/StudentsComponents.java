package com.esp.Component;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.model.StudentsHasStudyMaterials;
import com.esp.model.Students;
import com.esp.model.StudentsHasCourses;
import com.esp.model.StudentsHasExperts;
import com.esp.repository.StudentsHasCoursesRepository;
import com.esp.repository.StudentsHasExpertsRepository;
import com.esp.repository.StudentsHasStudyMaterialsRepository;
import com.esp.repository.StudentsRepository;

@Service
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
	 * @param id
	 * @return {@link Students}
	 */
	public Students findOneStudent(String id) {
		return studentsRepository.findOne(id);
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
	 * @param sh
	 */
	public void saveStudentsHasCourses(StudentsHasCourses sh) {
		studentHasCoursesRepository.save(sh);
	}
	
	/**
	 * To save in student has Experts tables
	 * @param sh
	 */
	public void saveStudentsHasExperts(StudentsHasExperts sh) {
		studentsHasExpertsRepository.save(sh);
	}
	
	/**
	 * To save the student has Study Material
	 * @param sh
	 */
	public void saveStudentsHasStudyMaterials(StudentsHasStudyMaterials sh) {
		studentsHasStudyMaterialsRepository.save(sh);
	}
	
	/**
	 * To delete row from student has study material
	 * @param sh
	 */
	public void deleteStudentsHasStudyMaterials(StudentsHasStudyMaterials sh) {
		studentsHasStudyMaterialsRepository.delete(sh);
	}
	


}
