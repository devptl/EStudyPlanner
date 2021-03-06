package com.esp.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esp.model.Courses;
import com.esp.model.Experts;
import com.esp.model.ExpertsHasCourses;
import com.esp.model.ExpertsHasStudyMaterials;
import com.esp.model.StudentsHasExperts;
import com.esp.model.StudyMaterials;
import com.esp.repository.CoursesRepository;
import com.esp.repository.ExpertsHasCoursesRepository;
import com.esp.repository.ExpertsHasStudyMaterialsRepository;
import com.esp.repository.ExpertsRepository;
import com.esp.repository.StudentsHasExpertsRepository;
import com.esp.repository.StudyMaterialsRepository;

/**
 * Component class to provide components on experts
 * allExperts()                                 -  Return list of all Experts
 * findOneExpert()                              -  Return a particular Expert
 * saveExpert()                                 -  To save a particular expert in database
 * findByExpertsUserNameAndCoursesIdCourse()    -  To find the expert with course and experts name
 * saveExpertsHasCourses() 						-  To save in experts has Courses tables
 * saveExpertsHasStudyMaterials() 				-  To save in experts has StudyMaterial tables
 * deleteExpertsHasStudyMaterials()		    	-  To delete from experts has StudyMaterial tables
 * expertsHasStudyMAterialWithUsername()	    -  List all the study material with a particular expert username
 * expertsHasStudyMAterialWithUsernameAndCouseId() - To get the expert has study material 
 * 													list accoriding to the experts username
 * findExpertsWithCoursesId()                   -  To get the list of experts according to the given course id
 * getExpertForStudent()                        -  To get the list of Experts student has applied for
 * getStudentsForExpert(                        -  To get the list of student following particular expert
 * 
 * @author mindfire
 *
 */
@Component
public class ExpertsComponents {

	@Autowired
	private ExpertsRepository expertsRepository;

	@Autowired
	private CoursesRepository coursesRepository;

	@Autowired
	private StudyMaterialsRepository studyMaterialsRepository;

	@Autowired
	private StudentsHasExpertsRepository studentsHasExpertsRepository;

	@Autowired
	private ExpertsHasCoursesRepository expertsHasCoursesRepository;

	@Autowired
	private ExpertsHasStudyMaterialsRepository expertsHasStudyMaterialsRepository;

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
	 * @param userName
	 *            - Experts UserName
	 * @return {@link Experts}
	 */
	public Experts findOneExpert(String userName) {
		return expertsRepository.findOne(userName);
	}

	/**
	 * To save a particular expert in database
	 * 
	 * @param Experts
	 */
	public void saveExpert(Experts expert) {
		expertsRepository.save(expert);
	}

	/**
	 * To find the expert with course and experts name
	 * 
	 * @param expertsUserName
	 * @param coursesIdCourse
	 * @return
	 */
	public ExpertsHasCourses findByExpertsUserNameAndCoursesIdCourse(String expertsUserName, int coursesIdCourse) {
		return expertsHasCoursesRepository.findByExpertsUserNameAndCoursesIdCourse(expertsUserName, coursesIdCourse);
	}

	/**
	 * To save in experts has Courses tables
	 * 
	 * @param ExpertsHasCourses
	 */
	public void saveExpertsHasCourses(ExpertsHasCourses expertsHasCourses) {
		expertsHasCoursesRepository.save(expertsHasCourses);
	}

	/**
	 * To save in experts has StudyMaterial tables
	 * 
	 * @param sh
	 *            - {@link ExpertsHasStudyMaterials}
	 */
	public void saveExpertsHasStudyMaterials(ExpertsHasStudyMaterials ex) {
		expertsHasStudyMaterialsRepository.save(ex);
	}

	/**
	 * To delete from experts has StudyMaterial tables
	 * 
	 * @param ex
	 *            - {@link ExpertsHasStudyMaterials}
	 */
	public void deleteExpertsHasStudyMaterials(ExpertsHasStudyMaterials ex) {
		expertsHasStudyMaterialsRepository.delete(ex);
	}

	/**
	 * list all the study material with a particular expert username
	 * 
	 * @param userName
	 *            - Experts UserName
	 * @return {@link ExpertsHasStudyMaterials}
	 */
	public ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMAterialWithUsername(String userName) {

		return expertsHasStudyMaterialsRepository.findByExpertsUserName(userName);
	}

	/**
	 * To get the expert has study material list accoriding to the experts username
	 * and course Id
	 * 
	 * @param id
	 *            - CourseID
	 * @param userName
	 *            - Experts UserName
	 * @return {@link ExpertsHasStudyMaterials}
	 */
	public ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMAterialWithUsernameAndCouseId(String id,
			String userName) {
		ArrayList<ExpertsHasStudyMaterials> myList = new ArrayList<>();

		// getting the complete list
		ArrayList<ExpertsHasStudyMaterials> mainList = expertsHasStudyMaterialsRepository
				.findByExpertsUserName(userName);

		for (int i = 0; i < mainList.size(); i++) {
			ExpertsHasStudyMaterials e = mainList.get(i);
			// getting the assoicated study material
			StudyMaterials s = studyMaterialsRepository.findOne(e.getStudyMaterialsIdStudyMaterials());
			// getting the associated course
			Courses c = coursesRepository.findOne(s.getCoursesIdCourse());
			if (c.getCourseName().equals(id)) {
				// adding the data to the list
				myList.add(e);
			}
		}

		return myList;
	}

	/**
	 * To get the list of experts according to the given course id
	 * 
	 * @param id
	 *            - CourseID
	 * @return {@link ExpertsHasCourses}
	 */
	public ArrayList<ExpertsHasCourses> findExpertsWithCoursesId(int id) {

		ArrayList<ExpertsHasCourses> mainList = expertsHasCoursesRepository.findByCoursesIdCourse(id);

		// sorting the list into descending order of ratitng
		Collections.sort(mainList, new Comparator<ExpertsHasCourses>() {
			@Override
			public int compare(ExpertsHasCourses o1, ExpertsHasCourses o2) {
				return o2.getRating() - o1.getRating();
			}
		});

		return mainList;
	}

	/**
	 * To get the list of Experts student has applied for
	 * 
	 * @param userName
	 *            - Students UserName
	 * @return {@link StudentsHasExperts}
	 */
	public ArrayList<StudentsHasExperts> getExpertForStudent(String userName) {

		return studentsHasExpertsRepository.findByStudentsUserName(userName);

	}

	/**
	 * To get the list of student following particular expert
	 * 
	 * @param userName
	 *            - Experts UserName
	 * @return {@link StudentsHasExperts}
	 */
	public ArrayList<StudentsHasExperts> getStudentsForExpert(String userName) {

		return studentsHasExpertsRepository.findByExpertsUserName(userName);

	}

}
