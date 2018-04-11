package com.esp.model;

public class RegisteredUser {

	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private int field;
	private String role;
	private int guardiansIdGuardians;
	private String password;
	private String qualification;
	private int yearOfExperience;

	public String getUserName() {
		return userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public int getField() {
		return field;
	}

	public int getGuardiansIdGuardians() {
		return guardiansIdGuardians;
	}

	public String getPassword() {
		return password;
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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setField(int field) {
		this.field = field;
	}

	public void setGuardiansIdGuardians(int guardiansIdGuardians) {
		this.guardiansIdGuardians = guardiansIdGuardians;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public void setYearOfExperience(int yearOfExperience) {
		this.yearOfExperience = yearOfExperience;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public RegisteredUser() {

	}

	public RegisteredUser(String userName, String firstName, String lastName, String email, int field, String role,
			int guardiansIdGuardians, String password, String qualification, int yearOfExperience) {

		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.field = field;
		this.role = role;
		this.guardiansIdGuardians = guardiansIdGuardians;
		this.password = password;
		this.qualification = qualification;
		this.yearOfExperience = yearOfExperience;
	}

}
