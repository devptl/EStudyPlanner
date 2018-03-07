package com.esp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esp.model.StudyMaterials;

@Repository
public interface StudyMaterialsRepository extends JpaRepository<StudyMaterials,Integer>{

}
