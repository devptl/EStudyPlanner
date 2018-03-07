package com.esp.model;

public class LoggedUser {

	private String userName;
	private String password;
	private int role;

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public int getRole() {
		return role;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public LoggedUser() {

	}

	public LoggedUser(String userName, String password, int role) {
		super();
		this.userName = userName;
		this.password = password;
		this.role = role;
	}

}
