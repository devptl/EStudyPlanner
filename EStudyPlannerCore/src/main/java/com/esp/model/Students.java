package com.esp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Students {

	@Id
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private int field;
	private int guardiansIdGuardians;
	private String password;

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

	public Students() {

	}

	public Students(String userName, String firstName, String lastName, String email, int field,
			int guardiansIdGuardians, String password) {

		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.field = field;
		this.guardiansIdGuardians = guardiansIdGuardians;
		this.password = password;
	}

}
