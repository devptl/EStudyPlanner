package com.esp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Experts {

	@Id
	private String userName;
	private String firstName;
	private String lastName;

	private String qualification;
	private int yearOfExperience;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public Experts(String userName, String firstName, String lastName, String qualification, int yearOfExperience,
			String password) {

		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.qualification = qualification;
		this.yearOfExperience = yearOfExperience;
		this.password = password;
	}

}