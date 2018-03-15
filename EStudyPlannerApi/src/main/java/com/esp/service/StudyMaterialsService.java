package com.esp.service;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.dto.DtoOperation;
import com.esp.model.ExpertsHasStudyMaterials;
import com.esp.model.StudyMaterials;

@Service
public class StudyMaterialsService {
	
	@Autowired
	private DtoOperation dtoOperation;
	
	@Autowired
	private ExpertsService expertsService;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public ArrayList<StudyMaterials> showStudyMaterialsByCourseid(int minorCourseId) {
		ArrayList<StudyMaterials> s1 = null;

		// To get the study material with the particular course id
		// Native SQL Query
		String queryString = "select * from study_materials"
				+ " where courses_id_course = "+ minorCourseId;
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, StudyMaterials.class);
		// Map result set to list of Objects
		s1 = (ArrayList<StudyMaterials>) query.getResultList();

		return s1;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<StudyMaterials> showStudyMaterialsByCourseName(String minorCourseName) {
		ArrayList<StudyMaterials> s1 = null;

		// To get the study material with particular course name 
		// Native SQL Query
		String queryString = "select s.id_study_materials,s.study_material_link,s.courses_id_course"
				+ " from study_materials s inner join courses c "
				+ "on s.courses_id_course=c.id_course "
				+ "and c.course_name =\""+minorCourseName+"\""; 
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, StudyMaterials.class);
		// Map result set to list of Objects
		s1 = (ArrayList<StudyMaterials>) query.getResultList();

		return s1;
	}
	
	
	/**
	 * To get the study materials list with expert name
	 * and the course name
	 * @param courseforstudymaterial
	 * @param expertsUserName
	 * @return
	 */
	public ArrayList<StudyMaterials> showStudyMaterialsByUserNameAndCourseId( String courseforstudymaterial,String expertsUserName) {
		ArrayList<StudyMaterials> s1 = new ArrayList<StudyMaterials>();
		
		//getting the list
		ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMaterials
		=expertsService.expertsHasStudyMAterialWithUsernameAndCouseId(courseforstudymaterial, expertsUserName);
		
		//setting the list to return to controller
		expertsHasStudyMaterials.forEach(x->s1.add(dtoOperation.getStudyMaterialsComponents().findOneStudyMaterial(x.getStudyMaterialsIdStudyMaterials())));

		return s1;
	}
	
	
	/**
	 * To list all the available study materials
	 * @return
	 */
	public ArrayList<StudyMaterials> allStudyMaterials()
	{
		return dtoOperation.getStudyMaterialsComponents().allStudyMaterials();
	}
	
	

}
