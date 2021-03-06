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
	private int rating;

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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public ExpertsHasCourses() {

	}

	public ExpertsHasCourses(String expertsUserName, int coursesIdCourse, int rating) {
		super();
		this.expertsUserName = expertsUserName;
		this.coursesIdCourse = coursesIdCourse;
		this.rating = rating;
	}

}
