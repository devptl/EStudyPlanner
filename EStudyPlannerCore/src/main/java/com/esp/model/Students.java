package com.esp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Students {

	@Id
	private String userName;
	private String firstName;
	private String lastName;

	private String city;
	private String state;
	private int zipCode;
	private int field;
	private int guardiansIdGuardians;
	
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

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public int getZipCode() {
		return zipCode;
	}

	public int getField() {
		return field;
	}

	public int getGuardiansIdGuardians() {
		return guardiansIdGuardians;
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

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public void setField(int field) {
		this.field = field;
	}

	public void setGuardiansIdGuardians(int guardiansIdGuardians) {
		this.guardiansIdGuardians = guardiansIdGuardians;
	}

	

	public Students() {

	}

	public Students(String userName, String firstName, String lastName, String city, String state, int zipCode,
			int field, int guardiansIdGuardians, String password) {
	
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.field = field;
		this.guardiansIdGuardians = guardiansIdGuardians;
		this.password = password;
	}

	
}
