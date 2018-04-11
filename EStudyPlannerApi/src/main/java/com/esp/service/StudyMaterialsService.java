package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.Component.StudentsComponents;
import com.esp.Component.StudyMaterialsComponents;
import com.esp.model.ExpertsHasStudyMaterials;
import com.esp.model.StudentsHasStudyMaterials;
import com.esp.model.StudyMaterials;

/**
 * Service class to provide service related to the study materials
 * showStudyMaterialsByCourseid()                -  To get the list of study material by Course ID
 * showStudyMaterialsByCourseName()              -  To get the list of study material with particular course name
 * showStudyMaterialsByUserNameAndCourseId()     -  To get the study materials list with expert name and the course name
 * allStudyMaterials()                           -  To list all the available study materials
 * getVedioEmbeddedLink()                        -  To get the embedded link from the embedded text
 * saveStudyMaterial()                           -  To Save the Study material in database
 * getCompletedList()                            -  To get the study material with the particular student username
 * saveStudentHasStudyMaterials()                -  To save the completed list to the database
 * getStudyMaterials()                           -  To get the study material list with the experts name
 * getStudyMaterialsForStudent()                 -  To get the study material list with student name
 * trackCourseCompletion()                       -  To track how much course he has completed
 * 
 * @author mindfire
 *
 */
@Service
public class StudyMaterialsService {

	@Autowired
	private StudentsComponents studentsComponents;

	@Autowired
	private StudyMaterialsComponents studyMaterialsComponents;

	@Autowired
	private ExpertsService expertsService;

	/**
	 * To get the list of study material by Course ID
	 * 
	 * @param minorCourseId
	 * @return {@link StudyMaterials}
	 */
	public ArrayList<StudyMaterials> showStudyMaterialsByCourseid(int minorCourseId) {

		return studyMaterialsComponents.showStudyMaterialsByCourseid(minorCourseId);
	}

	/**
	 * To get the list of study material with particular course name
	 * 
	 * @param minorCourseName
	 * @return {@link StudyMaterials}
	 */
	public ArrayList<StudyMaterials> showStudyMaterialsByCourseName(String minorCourseName) {

		ArrayList<StudyMaterials> studyMaterial = studyMaterialsComponents
				.showStudyMaterialsByCourseName(minorCourseName);

		return studyMaterial;

	}

	/**
	 * To get the study materials list with expert name and the course name
	 * 
	 * @param courseforstudymaterial
	 * @param expertsUserName
	 * @return {@link StudyMaterials}
	 */
	public ArrayList<StudyMaterials> showStudyMaterialsByUserNameAndCourseId(String courseforstudymaterial,
			String expertsUserName) {
		ArrayList<StudyMaterials> studyMaterials = new ArrayList<StudyMaterials>();

		// getting the list
		ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMaterials = expertsService
				.expertsHasStudyMAterialWithUsernameAndCouseId(courseforstudymaterial, expertsUserName);

		// setting the list to return to controller
		expertsHasStudyMaterials.forEach(x -> studyMaterials
				.add(studyMaterialsComponents.findOneStudyMaterial(x.getStudyMaterialsIdStudyMaterials())));

		return studyMaterials;
	}

	/**
	 * To list all the available study materials
	 * 
	 * @return {@link StudyMaterials}
	 */
	public ArrayList<StudyMaterials> allStudyMaterials() {
		return studyMaterialsComponents.allStudyMaterials();
	}

	/**
	 * To get the embedded link from the embedded text
	 * 
	 * @param link
	 * @return {@link String}
	 */
	public String getVedioEmbeddedLink(String link) {
		String[] mainlink = link.split("\"");
		String mylink = new String();
		for (int i = 0; i < mainlink.length; i++) {
			if (mainlink[i].startsWith("https"))
				mylink = mainlink[i];

		}
		return mylink;
	}

	/**
	 * To Save the Study material in database
	 * 
	 * @param coursId
	 * @param title
	 * @param link
	 */
	public void saveStudyMaterial(int coursId, String title, String link) {
		StudyMaterials studyMaterials = new StudyMaterials();

		// setting the Id title link
		studyMaterials.setCoursesIdCourse(coursId);
		studyMaterials.setTitle(title);
		studyMaterials.setStudyMaterialLink(link);
		studyMaterialsComponents.saveStudyMaterial(studyMaterials);
	}

	/**
	 * To get the study material with the particular student username
	 * 
	 * @param studentsUserName
	 * @return {@link StudentsHasStudyMaterials}
	 */
	public ArrayList<StudentsHasStudyMaterials> getCompletedList(String studentsUserName,
			String courseforstudymaterial) {

		return studyMaterialsComponents.getCompletedList(studentsUserName, courseforstudymaterial);

	}

	/**
	 * To save the completed list to the database
	 * 
	 * @param studyMaterialId
	 * @param studentsUserName
	 */
	public void saveStudentHasStudyMaterials(String[] studyMaterialId, String studentsUserName,
			String courseforstudymaterial) {
		ArrayList<StudentsHasStudyMaterials> studentsHasStudyMaterials = getCompletedList(studentsUserName,
				courseforstudymaterial);

		studentsHasStudyMaterials.forEach(x -> studentsComponents.deleteStudentsHasStudyMaterials(x));

		int i, id;
		for (i = 0; i < studyMaterialId.length; i++) {
			id = Integer.parseInt(studyMaterialId[i]);

			// saving the list to databse
			StudentsHasStudyMaterials s = new StudentsHasStudyMaterials(studentsUserName, id, "completed");
			studentsComponents.saveStudentsHasStudyMaterials(s);

		}
	}

	/**
	 * To get the study material list with the experts name
	 * 
	 * @param expertsHasStudyMaterials
	 * @return {@link StudyMaterials}
	 */
	public ArrayList<StudyMaterials> getStudyMaterials(ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMaterials) {
		ArrayList<StudyMaterials> studyMaterials = new ArrayList<StudyMaterials>();

		expertsHasStudyMaterials.forEach(x -> studyMaterials
				.add(studyMaterialsComponents.findOneStudyMaterial(x.getStudyMaterialsIdStudyMaterials())));

		return studyMaterials;
	}

	/**
	 * To get the study material list with student name
	 * 
	 * @param studentCompletedMaterials
	 * @return {@link StudyMaterials}
	 */
	public ArrayList<StudyMaterials> getStudyMaterialsForStudent(
			ArrayList<StudentsHasStudyMaterials> studentCompletedMaterials) {
		ArrayList<StudyMaterials> studyMaterials = new ArrayList<StudyMaterials>();

		// getting the list
		studentCompletedMaterials.forEach(x -> studyMaterials
				.add(studyMaterialsComponents.findOneStudyMaterial(x.getStudyMaterialsIdStudyMaterials())));

		return studyMaterials;
	}

	/**
	 * To track how much course he has completed
	 * 
	 * @param s1
	 *            - {@link StudyMaterials}
	 * @param studentCompletedMaterials
	 * @return {@link Float}
	 */
	public float trackCourseCompletion(ArrayList<StudyMaterials> s1,
			ArrayList<StudentsHasStudyMaterials> studentCompletedMaterials) {

		float perCompleted = 0;
		try {
			// all Study material allted to them
			float total = s1.size();

			// completed no of materials
			float completed = studentCompletedMaterials.size();
			perCompleted = ((completed * 100) / total);

		} catch (ArithmeticException arithmeticException) {
			perCompleted = 0;
		}

		return perCompleted;
	}

}
