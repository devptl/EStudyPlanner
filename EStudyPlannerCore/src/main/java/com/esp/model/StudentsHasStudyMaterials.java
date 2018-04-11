package com.esp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(StudentsHasStudyMaterialsJoinKey.class)
public class StudentsHasStudyMaterials {

	@Id
	private String studentsUserName;
	@Id
	private int studyMaterialsIdStudyMaterials;
	private String status;

	public String getStudentsUserName() {
		return studentsUserName;
	}

	public int getStudyMaterialsIdStudyMaterials() {
		return studyMaterialsIdStudyMaterials;
	}

	public String getStatus() {
		return status;
	}

	public void setStudentsUserName(String studentsUserName) {
		this.studentsUserName = studentsUserName;
	}

	public void setStudyMaterialsIdStudyMaterials(int studyMaterialsIdStudyMaterials) {
		this.studyMaterialsIdStudyMaterials = studyMaterialsIdStudyMaterials;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public StudentsHasStudyMaterials() {

	}

	public StudentsHasStudyMaterials(String studentsUserName, int studyMaterialsIdStudyMaterials, String status) {

		this.studentsUserName = studentsUserName;
		this.studyMaterialsIdStudyMaterials = studyMaterialsIdStudyMaterials;
		this.status = status;
	}

}
