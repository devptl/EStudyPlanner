package com.esp.Component;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esp.model.Courses;
import com.esp.model.StudentsHasStudyMaterials;
import com.esp.model.StudyMaterials;
import com.esp.repository.CoursesRepository;
import com.esp.repository.StudentsHasStudyMaterialsRepository;
import com.esp.repository.StudyMaterialsRepository;

/**
 * Component class to provide components on study materials
 * allStudyMaterials()                              -  Return list of all StudyMaterials
 * findOneStudyMaterial()                           -  Return a particular StudyMaterial
 * saveStudyMaterial()                              -  To save a particular study material in database
 * showStudyMaterialsByCourseid()                   -  To get the list of study material by Course ID
 * showStudyMaterialsByCourseName()                 -  To get the list of study material with particular course name
 * getCompletedList()                               -  To get the study material with the particular student username
 * 
 * @author mindfire
 *
 */
@Component
public class StudyMaterialsComponents {

	@Autowired
	private StudyMaterialsRepository studyMaterialsRepository;

	@Autowired
	private CoursesRepository coursesRepository;

	@Autowired
	private StudentsHasStudyMaterialsRepository studentsHasStudyMaterialsRepository;

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
	 *            - StudyMaterialID
	 * @return {@link StudyMaterials}
	 */
	public StudyMaterials findOneStudyMaterial(int id) {
		return studyMaterialsRepository.findOne(id);
	}

	/**
	 * To save a particular study material in database
	 * 
	 * @param StudyMaterial
	 */
	public void saveStudyMaterial(StudyMaterials s) {
		studyMaterialsRepository.save(s);
	}

	/**
	 * To get the list of study material by Course ID
	 * 
	 * @param minorCourseId
	 * @return {@link StudyMaterials}
	 */
	public ArrayList<StudyMaterials> showStudyMaterialsByCourseid(int minorCourseId) {

		return studyMaterialsRepository.findByCoursesIdCourse(minorCourseId);
	}

	/**
	 * To get the list of study material with particular course name
	 * 
	 * @param minorCourseName
	 * @return {@link StudyMaterials}
	 */
	public ArrayList<StudyMaterials> showStudyMaterialsByCourseName(String minorCourseName) {
		ArrayList<StudyMaterials> myList = new ArrayList<>();

		// getting the complete list
		ArrayList<StudyMaterials> mainList = (ArrayList<StudyMaterials>) studyMaterialsRepository.findAll();

		for (int i = 0; i < mainList.size(); i++) {
			StudyMaterials s = mainList.get(i);
			// getting the associated course
			Courses c = coursesRepository.findOne(s.getCoursesIdCourse());
			// checking if the course name matched to given name
			if (c.getCourseName().equals(minorCourseName)) {
				myList.add(s);
			}
		}
		return myList;
	}

	/**
	 * To get the study material with the particular student username
	 * 
	 * @param studentsUserName
	 * @return {@link StudentsHasStudyMaterials}
	 */
	public ArrayList<StudentsHasStudyMaterials> getCompletedList(String studentsUserName,
			String courseforstudymaterial) {
		ArrayList<StudentsHasStudyMaterials> myList = new ArrayList<>();

		// getting the complete list by student useername
		ArrayList<StudentsHasStudyMaterials> mainList = studentsHasStudyMaterialsRepository
				.findByStudentsUserName(studentsUserName);

		for (int i = 0; i < mainList.size(); i++) {
			StudentsHasStudyMaterials sh = mainList.get(i);
			// finding associated study material
			StudyMaterials s = studyMaterialsRepository.findOne(sh.getStudyMaterialsIdStudyMaterials());
			// finding the associated course
			Courses c = coursesRepository.findOne(s.getCoursesIdCourse());
			// checking if the course id matched
			if (c.getCourseName().equals(courseforstudymaterial)) {
				myList.add(sh);
			}
		}
		return myList;

	}

}
