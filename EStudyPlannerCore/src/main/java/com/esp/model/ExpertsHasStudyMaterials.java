package com.esp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ExpertsHasStudyMaterialsJoinKey.class)
public class ExpertsHasStudyMaterials {

	@Id
	private String expertsUserName;
	@Id
	private int studyMaterialsIdStudyMaterials;

	public String getExpertsUserName() {
		return expertsUserName;
	}

	public int getStudyMaterialsIdStudyMaterials() {
		return studyMaterialsIdStudyMaterials;
	}

	public void setExpertsUserName(String expertsUserName) {
		this.expertsUserName = expertsUserName;
	}

	public void setStudyMaterialsIdStudyMaterials(int studyMaterialsIdStudyMaterials) {
		this.studyMaterialsIdStudyMaterials = studyMaterialsIdStudyMaterials;
	}

	public ExpertsHasStudyMaterials() {

	}

	public ExpertsHasStudyMaterials(String expertsUserName, int studyMaterialsIdStudyMaterials) {

		this.expertsUserName = expertsUserName;
		this.studyMaterialsIdStudyMaterials = studyMaterialsIdStudyMaterials;
	}

}
