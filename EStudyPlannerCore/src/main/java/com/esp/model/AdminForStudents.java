package com.esp.model;

import java.util.ArrayList;

public class AdminForStudents {

	private Students students;
	private ArrayList<Courses> coursesList;
	private ArrayList<StudentsHasExperts> expertslist;
	private ArrayList<StudyMaterials> studyMaterialsList;

	public Students getStudents() {
		return students;
	}

	public ArrayList<Courses> getCoursesList() {
		return coursesList;
	}

	public ArrayList<StudyMaterials> getStudyMaterialsList() {
		return studyMaterialsList;
	}

	public void setStudents(Students students) {
		this.students = students;
	}

	public void setCoursesList(ArrayList<Courses> coursesList) {
		this.coursesList = coursesList;
	}

	public ArrayList<StudentsHasExperts> getExpertslist() {
		return expertslist;
	}

	public void setExpertslist(ArrayList<StudentsHasExperts> expertslist) {
		this.expertslist = expertslist;
	}

	public void setStudyMaterialsList(ArrayList<StudyMaterials> studyMaterialsList) {
		this.studyMaterialsList = studyMaterialsList;
	}

	public AdminForStudents() {

	}

	public AdminForStudents(Students students, ArrayList<Courses> coursesList,
			ArrayList<StudentsHasExperts> expertslist, ArrayList<StudyMaterials> studyMaterialsList) {

		this.students = students;
		this.coursesList = coursesList;
		this.expertslist = expertslist;
		this.studyMaterialsList = studyMaterialsList;
	}

}
