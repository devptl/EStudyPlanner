package com.esp.model;

import java.util.ArrayList;

public class AdminForExperts {

	private Experts experts;
	private ArrayList<Courses> coursesList;
	private ArrayList<StudyMaterials> studyMaterials;
	private ArrayList<StudentsHasExperts> studentsHasExpertsList;

	public Experts getExperts() {
		return experts;
	}

	public ArrayList<Courses> getCoursesList() {
		return coursesList;
	}

	public ArrayList<StudyMaterials> getStudyMaterials() {
		return studyMaterials;
	}

	public ArrayList<StudentsHasExperts> getStudentsHasExpertsList() {
		return studentsHasExpertsList;
	}

	public void setExperts(Experts experts) {
		this.experts = experts;
	}

	public void setCoursesList(ArrayList<Courses> coursesList) {
		this.coursesList = coursesList;
	}

	public void setStudyMaterials(ArrayList<StudyMaterials> studyMaterials) {
		this.studyMaterials = studyMaterials;
	}

	public void setStudentsHasExpertsList(ArrayList<StudentsHasExperts> studentsHasExpertsList) {
		this.studentsHasExpertsList = studentsHasExpertsList;
	}

	public AdminForExperts() {

	}

	public AdminForExperts(Experts experts, ArrayList<Courses> coursesList, ArrayList<StudyMaterials> studyMaterials,
			ArrayList<StudentsHasExperts> studentsHasExpertsList) {

		this.experts = experts;
		this.coursesList = coursesList;
		this.studyMaterials = studyMaterials;
		this.studentsHasExpertsList = studentsHasExpertsList;
	}

}
