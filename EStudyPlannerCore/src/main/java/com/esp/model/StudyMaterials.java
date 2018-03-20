package com.esp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StudyMaterials {

	@Id
	private int idStudyMaterials;
	private String studyMaterialLink;
	private int coursesIdCourse;
	private String title;
	

	public int getIdStudyMaterials() {
		return idStudyMaterials;
	}

	public String getStudyMaterialLink() {
		return studyMaterialLink;
	}

	public int getCoursesIdCourse() {
		return coursesIdCourse;
	}

	public void setIdStudyMaterials(int idStudyMaterials) {
		this.idStudyMaterials = idStudyMaterials;
	}

	public void setStudyMaterialLink(String studyMaterialLink) {
		this.studyMaterialLink = studyMaterialLink;
	}

	public void setCoursesIdCourse(int coursesIdCourse) {
		this.coursesIdCourse = coursesIdCourse;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public StudyMaterials() {

	}

	public StudyMaterials(int idStudyMaterials, String studyMaterialLink, int coursesIdCourse, String title) {
		super();
		this.idStudyMaterials = idStudyMaterials;
		this.studyMaterialLink = studyMaterialLink;
		this.coursesIdCourse = coursesIdCourse;
		this.title = title;
	}

	

}
