package com.aurionpro.user;

public class Admin implements User {
	
	private String username;
	private String password;

	public Admin(String username, String password) {
	    this.username = username;
	    this.password = password;
	}

	@Override
	public String getUsername() {
	    return this.username;
	}

	@Override
	public String getPassword() {
	    return this.password;
	}

	@Override
	public String toString() {
	    return "Admin{username='" + username + "'}";
	}
}