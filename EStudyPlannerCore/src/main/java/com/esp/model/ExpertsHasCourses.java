package com.esp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ExpertsHasCoursesJoinKey.class)
public class ExpertsHasCourses {

	@Id
	private String expertsUserName;
	@Id
	private int coursesIdCourse;

	public String getExpertsUserName() {
		return expertsUserName;
	}

	public int getCoursesIdCourse() {
		return coursesIdCourse;
	}

	public void setExpertsUserName(String expertsUserName) {
		this.expertsUserName = expertsUserName;
	}

	public void setCoursesIdCourse(int coursesIdCourse) {
		this.coursesIdCourse = coursesIdCourse;
	}

	public ExpertsHasCourses() {

	}

	public ExpertsHasCourses(String expertsUserName, int coursesIdCourse) {
		super();
		this.expertsUserName = expertsUserName;
		this.coursesIdCourse = coursesIdCourse;
	}

}
