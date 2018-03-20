package com.esp.model;

public class LoggedUser {

	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoggedUser() {

	}

	public LoggedUser(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;

	}

}
