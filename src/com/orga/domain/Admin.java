package com.orga.domain;

public class Admin {
	/*����Ա�û���*/
	private String username;
	/*��½����*/
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString() {
		return " username:" + username;
	}
}