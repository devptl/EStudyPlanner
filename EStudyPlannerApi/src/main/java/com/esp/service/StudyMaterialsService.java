package com.esp.service;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

	@PersistenceContext
	EntityManager entityManager;

	/**
	 * To get the list of study material by Course ID
	 * 
	 * @param minorCourseId
	 * @return {@link StudyMaterials}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<StudyMaterials> showStudyMaterialsByCourseid(int minorCourseId) {
		ArrayList<StudyMaterials> s1 = null;

		// To get the study material with the particular course id
		// Native SQL Query
		String queryString = "select * from study_materials" + " where courses_id_course = " + minorCourseId;
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, StudyMaterials.class);
		// Map result set to list of Objects
		s1 = (ArrayList<StudyMaterials>) query.getResultList();

		return s1;
	}

	/**
	 * To get the list of study material with particular course name
	 * 
	 * @param minorCourseName
	 * @return {@link StudyMaterials}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<StudyMaterials> showStudyMaterialsByCourseName(String minorCourseName) {
		ArrayList<StudyMaterials> s1 = null;

		// To get the study material with particular course name
		// Native SQL Query
		String queryString = "select s.id_study_materials,s.study_material_link,s.courses_id_course,s.title"
				+ " from study_materials s inner join courses c " + "on s.courses_id_course=c.id_course "
				+ "and c.course_name =\"" + minorCourseName + "\"";
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, StudyMaterials.class);
		// Map result set to list of Objects
		s1 = (ArrayList<StudyMaterials>) query.getResultList();

		return s1;
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
		ArrayList<StudyMaterials> s1 = new ArrayList<StudyMaterials>();

		// getting the list
		ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMaterials = expertsService
				.expertsHasStudyMAterialWithUsernameAndCouseId(courseforstudymaterial, expertsUserName);

		// setting the list to return to controller
		expertsHasStudyMaterials.forEach(x -> s1.add(dtoOperation.getStudyMaterialsComponents()
				.findOneStudyMaterial(x.getStudyMaterialsIdStudyMaterials())));

		return s1;
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
		StudyMaterials s = new StudyMaterials();

		// setting the Id title link
		s.setCoursesIdCourse(coursId);
		s.setTitle(title);
		s.setStudyMaterialLink(link);
		dtoOperation.getStudyMaterialsComponents().saveStudyMaterial(s);
	}

	/**
	 * To get the study material with the particular student username
	 * 
	 * @param studentsUserName
	 * @return {@link StudentsHasStudyMaterials}
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<StudentsHasStudyMaterials> getCompletedList(String studentsUserName) {
		ArrayList<StudentsHasStudyMaterials> s1 = null;

		// To get the study material with particular Student username
		// Native SQL Query
		String queryString = "select * from students_has_study_materials where students_user_name = \""
				+ studentsUserName + "\"";
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, StudentsHasStudyMaterials.class);
		// Map result set to list of Objects
		s1 = (ArrayList<StudentsHasStudyMaterials>) query.getResultList();

		return s1;

	}

	/**
	 * To save the completed list to the database
	 * 
	 * @param studyMaterialId
	 * @param studentsUserName
	 */
	public void saveStudentHasStudyMaterials(String[] studyMaterialId, String studentsUserName) {
		ArrayList<StudentsHasStudyMaterials> s1 = getCompletedList(studentsUserName);

		s1.forEach(x -> dtoOperation.getStudentsComponents().deleteStudentsHasStudyMaterials(x));

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
		ArrayList<StudyMaterials> s1 = new ArrayList<StudyMaterials>();

		expertsHasStudyMaterials.forEach(x -> s1.add(dtoOperation.getStudyMaterialsComponents()
				.findOneStudyMaterial(x.getStudyMaterialsIdStudyMaterials())));

		return s1;
	}

	/**
	 * To get the study material list with student name
	 * 
	 * @param studentCompletedMaterials
	 * @return {@link StudyMaterials}
	 */
	public ArrayList<StudyMaterials> getStudyMaterialsForStudent(
			ArrayList<StudentsHasStudyMaterials> studentCompletedMaterials) {
		ArrayList<StudyMaterials> s1 = new ArrayList<StudyMaterials>();

		// getting the list
		studentCompletedMaterials.forEach(x -> s1.add(dtoOperation.getStudyMaterialsComponents()
				.findOneStudyMaterial(x.getStudyMaterialsIdStudyMaterials())));

		return s1;
	}

	/**
	 * To track how much course he has completed
	 * 
	 * @param s1 - {@link StudyMaterials}
	 * @param studentCompletedMaterials
	 * @return {@link Float}
	 */
	public float trackCourseCompletion(ArrayList<StudyMaterials> s1,
			ArrayList<StudentsHasStudyMaterials> studentCompletedMaterials) {
		float total = s1.size();
		float completed = studentCompletedMaterials.size();
		float perCompleted = ((completed * 100) / total);

		return perCompleted;
	}

}
