package com.esp.Component;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.Schedule;
import com.esp.model.Students;
import com.esp.model.StudentsHasCourses;
import com.esp.model.StudyMaterials;
import com.esp.repository.CoursesRepository;
import com.esp.repository.ExpertsRepository;
import com.esp.repository.ScheduleRepository;
import com.esp.repository.StudentHasCoursesRepository;
import com.esp.repository.StudentsRepository;
import com.esp.repository.StudyMaterialsRepository;

@Service
public class Components {

	@Autowired
	private CoursesRepository coursesRepository;

	@Autowired
	private ExpertsRepository expertsRepository;

	@Autowired
	private StudyMaterialsRepository studyMaterialsRepository;

	@Autowired
	private StudentsRepository studentsRepository;
	
	@Autowired
	private ScheduleRepository scheduleRepository; 
	
	@Autowired
	private StudentHasCoursesRepository studentHasCoursesRepository; 

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

	/**
	 * return list of all Experts
	 * 
	 * @return {@link Experts}
	 */
	public ArrayList<Experts> allExperts() {
		return (ArrayList<Experts>) expertsRepository.findAll();
	}

	/**
	 * return a particular Expert
	 * 
	 * @param id
	 * @return {@link Experts}
	 */
	public Experts findOneExpert(String id) {
		return expertsRepository.findOne(id);
	}
	
	/**
	 * To save a particular expert in database
	 * @param Experts
	 */
	public void saveExpert(Experts e) {
		expertsRepository.save(e);
	}

	/**
	 * return list of all StudyMaterials
	 * 
	 * @return {@link StudyMaterials}
	 */
	public ArrayList<StudyMaterials> allStudyMaterials() {
		return (ArrayList<StudyMaterials>) studyMaterialsRepository.findAll();
	}

	/**
	 * return a particular StudyMaterial
	 * 
	 * @param id
	 * @return {@link StudyMaterials}
	 */
	public StudyMaterials findOneStudyMaterial(int id) {
		return studyMaterialsRepository.findOne(id);
	}
	
	/**
	 * To save a particular study material in database
	 * @param StudyMaterial
	 */
	public void saveStudyMaterial(StudyMaterials s) {
		studyMaterialsRepository.save(s);
	}

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
	 * @param Students
	 */
	public void saveStudent(Students s) {
		studentsRepository.save(s);
	}
	
	/**
	 * To get a particular schedule
	 * @param id
	 * @return {@link Schedule}
	 */
	public Schedule findOneSchedule(String id) {
		return scheduleRepository.findOne(id); 
	}
	
	/**
	 * To save a schedule in database
	 * @param s
	 */
	public void saveSchedule(Schedule s) {
		scheduleRepository.save(s);
	}
	
    public void saveStudentsHasCourses(StudentsHasCourses sh)
    {
    	studentHasCoursesRepository.save(sh);
    }
	

}
