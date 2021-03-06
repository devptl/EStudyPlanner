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
@Table(name = "students")
public class Students {

	@Id
	private String userName;
	private int field;
	private int guardiansIdGuardians;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "studentsHasCourses", joinColumns = {
			@JoinColumn(name = "studentsUserName") }, inverseJoinColumns = { @JoinColumn(name = "coursesIdCourse") })
	private Set<Courses> courses = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "studentsHasExperts", joinColumns = {
			@JoinColumn(name = "studentsUserName") }, inverseJoinColumns = { @JoinColumn(name = "expertsUserName") })
	private Set<Experts> experts = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "studentsHasStudyMaterials", joinColumns = {
			@JoinColumn(name = "studentsUserName") }, inverseJoinColumns = {
					@JoinColumn(name = "studyMaterialsIdStudyMaterials") })
	private Set<StudyMaterials> studyMaterials = new HashSet<>();

	public Set<Experts> getExperts() {
		return experts;
	}

	public Set<StudyMaterials> getStudyMaterials() {
		return studyMaterials;
	}

	public void setExperts(Set<Experts> experts) {
		this.experts = experts;
	}

	public void setStudyMaterials(Set<StudyMaterials> studyMaterials) {
		this.studyMaterials = studyMaterials;
	}

	public Set<Courses> getCourses() {
		return courses;
	}

	public void setCourses(Set<Courses> courses) {
		this.courses = courses;
	}

	public String getUserName() {
		return userName;
	}

	public int getField() {
		return field;
	}

	public int getGuardiansIdGuardians() {
		return guardiansIdGuardians;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setField(int field) {
		this.field = field;
	}

	public void setGuardiansIdGuardians(int guardiansIdGuardians) {
		this.guardiansIdGuardians = guardiansIdGuardians;
	}

	public Students() {

	}

	public Students(String userName, int field, int guardiansIdGuardians) {

		this.userName = userName;
		this.field = field;
		this.guardiansIdGuardians = guardiansIdGuardians;
	}

}
