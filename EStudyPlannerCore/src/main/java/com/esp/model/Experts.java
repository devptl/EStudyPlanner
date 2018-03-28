package com.esp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "experts")
public class Experts {

	@Id
	private String userName;
	private String qualification;
	private int yearOfExperience;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "expertsHasStudyMaterials", joinColumns = {
			@JoinColumn(name = "expertsUserName") }, inverseJoinColumns = {
					@JoinColumn(name = "studyMaterialsIdStudyMaterials") })
	private Set<StudyMaterials> studyMaterials = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "ExpertsHasCourses", joinColumns = {
			@JoinColumn(name = "expertsUserName") }, inverseJoinColumns = { @JoinColumn(name = "coursesIdCourse") })
	private Set<Courses> courses = new HashSet<>();

	public Set<StudyMaterials> getStudyMaterials() {
		return studyMaterials;
	}

	public Set<Courses> getCourses() {
		return courses;
	}

	public void setCourses(Set<Courses> courses) {
		this.courses = courses;
	}

	public void setStudyMaterials(Set<StudyMaterials> studyMaterials) {
		this.studyMaterials = studyMaterials;
	}

	public String getUserName() {
		return userName;
	}

	public String getQualification() {
		return qualification;
	}

	public int getYearOfExperience() {
		return yearOfExperience;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public void setYearOfExperience(int yearOfExperience) {
		this.yearOfExperience = yearOfExperience;
	}

	public Experts() {
	}

	public Experts(String userName, String qualification, int yearOfExperience) {
		super();
		this.userName = userName;
		this.qualification = qualification;
		this.yearOfExperience = yearOfExperience;
	}

}