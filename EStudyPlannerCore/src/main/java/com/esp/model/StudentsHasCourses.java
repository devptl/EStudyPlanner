package com.esp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(StudentsHasCoursesJoinKey.class)
public class StudentsHasCourses {

	@Id
	private String studentsUserName;
    @Id
	private int coursesIdCourse;

	public String getStudentsUserName() {
		return studentsUserName;
	}

	public int getCoursesIdCourse() {
		return coursesIdCourse;
	}

	public void setStudentsUserName(String studentsUserName) {
		this.studentsUserName = studentsUserName;
	}

	public void setCoursesIdCourse(int coursesIdCourse) {
		this.coursesIdCourse = coursesIdCourse;
	}

	public StudentsHasCourses() {

	}

	public StudentsHasCourses(String studentsUserName, int coursesIdCourse) {

		this.studentsUserName = studentsUserName;
		this.coursesIdCourse = coursesIdCourse;
	}

}
