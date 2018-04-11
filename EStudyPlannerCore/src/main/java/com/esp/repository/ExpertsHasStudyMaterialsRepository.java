package com.esp.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esp.model.ExpertsHasStudyMaterials;

public interface ExpertsHasStudyMaterialsRepository extends JpaRepository<ExpertsHasStudyMaterials,String>{
	
	public ArrayList<ExpertsHasStudyMaterials> findByExpertsUserName(String expertsUserName);

}
