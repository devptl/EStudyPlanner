package com.esp.Component;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.model.Experts;
import com.esp.model.ExpertsHasCourses;
import com.esp.model.ExpertsHasStudyMaterials;
import com.esp.repository.ExpertsHasCoursesRepository;
import com.esp.repository.ExpertsHasStudyMaterialsRepository;
import com.esp.repository.ExpertsRepository;

@Service
public class ExpertsComponents {

	@Autowired
	private ExpertsRepository expertsRepository;
	
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
	 * @param id
	 * @return {@link Experts}
	 */
	public Experts findOneExpert(String id) {
		return expertsRepository.findOne(id);
	}

	/**
	 * To save a particular expert in database
	 * 
	 * @param Experts
	 */
	public void saveExpert(Experts e) {
		expertsRepository.save(e);
	}
	
	/**
	 * To save in experts has Courses tables
	 * @param sh
	 */
	public void saveExpertsHasCourses(ExpertsHasCourses ex) {
		expertsHasCoursesRepository.save(ex);
	}
	
	/**
	 * To save in experts has StudyMaterial tables
	 * @param sh
	 */
	public void saveExpertsHasStudyMaterials(ExpertsHasStudyMaterials ex) {
		expertsHasStudyMaterialsRepository.save(ex);
	}
	

}
