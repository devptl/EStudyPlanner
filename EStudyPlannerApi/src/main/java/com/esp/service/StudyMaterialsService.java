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

		// To get the card type for a particular bank id
		// Native SQL Query
		String queryString = "select * from study_materials"
				+ " where courses_id_course = "+ minorCourseId;
		// Generate Query
		Query query = entityManager.createNativeQuery(queryString, StudyMaterials.class);
		// Map result set to list of Objects
		s1 = (ArrayList<StudyMaterials>) query.getResultList();

		return s1;
	}
	
	

	public ArrayList<StudyMaterials> showStudyMaterialsByUserNameAndCourseId( String courseforstudymaterial,String expertsUserName) {
		ArrayList<StudyMaterials> s1 = new ArrayList<StudyMaterials>();
		
		ArrayList<ExpertsHasStudyMaterials> expertsHasStudyMaterials
		=expertsService.expertsHasStudyMAterialWithUsernameAndCouseId(courseforstudymaterial, expertsUserName);
		
		expertsHasStudyMaterials.forEach(x->s1.add(dtoOperation.getStudyMaterialsComponents().findOneStudyMaterial(x.getStudyMaterialsIdStudyMaterials())));

		return s1;
	}
	
	
	
	public ArrayList<StudyMaterials> allStudyMaterials()
	{
		return dtoOperation.getStudyMaterialsComponents().allStudyMaterials();
	}
	
	

}
