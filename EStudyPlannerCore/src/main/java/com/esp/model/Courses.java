package com.esp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Courses {

	@Id
	private int idCourse;
	private String courseName;
	private float courseDuration;
	private int parentCourseId;

	public int getIdCourse() {
		return idCourse;
	}

	public String getCourseName() {
		return courseName;
	}

	public float getCourseDuration() {
		return courseDuration;
	}

	public int getParentCourseId() {
		return parentCourseId;
	}

	public void setIdCourse(int idCourse) {
		this.idCourse = idCourse;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setCourseDuration(float courseDuration) {
		this.courseDuration = courseDuration;
	}

	public void setParentCourseId(int parentCourseId) {
		this.parentCourseId = parentCourseId;
	}

	public Courses() {

	}

	public Courses(int idCourse, String courseName, float courseDuration, int parentCourseId) {
		this.idCourse = idCourse;
		this.courseName = courseName;
		this.courseDuration = courseDuration;
		this.parentCourseId = parentCourseId;
	}

}
