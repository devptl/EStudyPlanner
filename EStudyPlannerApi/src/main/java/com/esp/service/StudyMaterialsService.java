package com.esp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.dto.DtoOperation;
import com.esp.model.ExpertsHasStudyMaterials;
import com.esp.model.StudentsHasStudyMaterials;
import com.esp.model.StudyMaterials;

@Service
public class StudyMaterialsService {

	@Autowired
	private DtoOperation dtoOperation;

	@Autowired
	private ExpertsService expertsService;

	/**
	 * To get the list of study material by Course ID
	 * 
	 * @param minorCourseId
	 * @return {@link StudyMaterials}
	 */
	public ArrayList<StudyMaterials> showStudyMaterialsByCourseid(int minorCourseId) {

		return dtoOperation.getStudyMaterialsComponents().showStudyMaterialsByCourseid(minorCourseId);
	}

	/**
	 * To get the list of study material with particular course name
	 * 
	 * @param minorCourseName
	 * @return {@link StudyMaterials}
	 */
	public ArrayList<StudyMaterials> showStudyMaterialsByCourseName(String minorCourseName) {

		ArrayList<StudyMaterials> studyMaterial = dtoOperation.getStudyMaterialsComponents().showStudyMaterialsByCourseName(minorCourseName);
        
        return studyMaterial;
	
	}

	/**
	 * To get the study materials list with expert name and the course name
	 * 
	 * @param courseforstudymaterial
	 * @param expertsUserName
	 * @return
	 */
	public ArrayList<StudyMaterials> showStudyMaterialsByUserNameAndCourseId(String courseforstudymaterial,
			String expertsUserName) {
		ArrayList<StudyMaterials> studyMaterials = new ArrayList<StudyMaterials>();

		// getting the list
		ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMaterials = expertsService
				.expertsHasStudyMAterialWithUsernameAndCouseId(courseforstudymaterial, expertsUserName);

		// setting the list to return to controller
		expertsHasStudyMaterials.forEach(x -> studyMaterials.add(dtoOperation.getStudyMaterialsComponents()
				.findOneStudyMaterial(x.getStudyMaterialsIdStudyMaterials())));

		return studyMaterials;
	}

	/**
	 * To list all the available study materials
	 * 
	 * @return {@link StudyMaterials}
	 */
	public ArrayList<StudyMaterials> allStudyMaterials() {
		return dtoOperation.getStudyMaterialsComponents().allStudyMaterials();
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
		dtoOperation.getStudyMaterialsComponents().saveStudyMaterial(studyMaterials);
	}

	/**
	 * To get the study material with the particular student username
	 * 
	 * @param studentsUserName
	 * @return {@link StudentsHasStudyMaterials}
	 */
	public ArrayList<StudentsHasStudyMaterials> getCompletedList(String studentsUserName,
			String courseforstudymaterial) {

		return dtoOperation.getStudyMaterialsComponents().getCompletedList(studentsUserName, courseforstudymaterial);

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

		studentsHasStudyMaterials.forEach(x -> dtoOperation.getStudentsComponents().deleteStudentsHasStudyMaterials(x));

		int i, id;
		for (i = 0; i < studyMaterialId.length; i++) {
			id = Integer.parseInt(studyMaterialId[i]);

			// saving the list to databse
			StudentsHasStudyMaterials s = new StudentsHasStudyMaterials(studentsUserName, id, "completed");
			dtoOperation.getStudentsComponents().saveStudentsHasStudyMaterials(s);

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

		expertsHasStudyMaterials.forEach(x -> studyMaterials.add(dtoOperation.getStudyMaterialsComponents()
				.findOneStudyMaterial(x.getStudyMaterialsIdStudyMaterials())));

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
		studentCompletedMaterials.forEach(x -> studyMaterials.add(dtoOperation.getStudyMaterialsComponents()
				.findOneStudyMaterial(x.getStudyMaterialsIdStudyMaterials())));

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
		
		float perCompleted=0;
		try {
		float total = s1.size();
		float completed = studentCompletedMaterials.size();
		perCompleted = ((completed * 100) / total);
		
		}catch(Exception e)
		{
			perCompleted=0;
		}

		return perCompleted;
	}

}
