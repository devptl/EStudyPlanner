package com.esp.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esp.model.StudentsHasStudyMaterials;

public interface StudentsHasStudyMaterialsRepository extends JpaRepository<StudentsHasStudyMaterials,String>{
	
	public ArrayList<StudentsHasStudyMaterials> findByStudentsUserName(String studentsUserName);

}
