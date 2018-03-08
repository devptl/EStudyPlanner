package com.esp.Component;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esp.model.StudyMaterials;
import com.esp.repository.StudyMaterialsRepository;

@Service
public class StudyMaterialsComponents {
	
	@Autowired
	private StudyMaterialsRepository studyMaterialsRepository;
	
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


}
