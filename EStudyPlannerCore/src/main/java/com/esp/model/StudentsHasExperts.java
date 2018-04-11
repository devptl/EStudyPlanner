package com.esp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(StudentsHasExpertsJoinKey.class)
public class StudentsHasExperts {

	@Id
	private String studentsUserName;
	@Id
	private String expertsUserName;

	public String getStudentsUserName() {
		return studentsUserName;
	}

	public String getExpertsUserName() {
		return expertsUserName;
	}

	public void setStudentsUserName(String studentsUserName) {
		this.studentsUserName = studentsUserName;
	}

	public void setExpertsUserName(String expertsUserName) {
		this.expertsUserName = expertsUserName;
	}

	public StudentsHasExperts() {

	}

	public StudentsHasExperts(String studentsUserName, String expertsUserName) {
		this.studentsUserName = studentsUserName;
		this.expertsUserName = expertsUserName;
	}

}
