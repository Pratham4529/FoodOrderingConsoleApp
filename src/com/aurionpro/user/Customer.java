package com.aurionpro.user;

public class Customer implements User {
	
	private String username;
	private String password;
	private String customerId;
	private String fullName;
	
	
	public Customer(String username, String password, String customerId, String fullName) {
	    this.username = username;
	    this.password = password;
	    this.customerId = customerId;
	    this.fullName = fullName;
	}

	
	@Override
	public String getUsername() {
	    return this.username;
	}

	@Override
	public String getPassword() {
	    return this.password;
	}

	public String getCustomerId() {
	    return this.customerId;
	}

	public String getFullName() {
	    return this.fullName;
	}

	
	@Override
	public String toString() {
	    return "Customer{" +
	            "username='" + username + '\'' +
	            ", customerId='" + customerId + '\'' +
	            ", fullName='" + fullName + '\'' +
	            '}';
	}

}
