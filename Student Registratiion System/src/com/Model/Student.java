package com.Model;

public class Student {
	private String StdEmail;
	private String StdPassword;
	private String StdName;
	
	public Student() {
		
	}

	public Student(String stdEmail, String stdPassword, String stdName) {
		super();
		StdEmail = stdEmail;
		StdPassword = stdPassword;
		StdName = stdName;
	}

	public String getStdEmail() {
		return StdEmail;
	}

	public void setStdEmail(String stdEmail) {
		StdEmail = stdEmail;
	}

	public String getStdPassword() {
		return StdPassword;
	}

	public void setStdPassword(String stdPassword) {
		StdPassword = stdPassword;
	}

	public String getStdName() {
		return StdName;
	}

	public void setStdName(String stdName) {
		StdName = stdName;
	}

	@Override
	public String toString() {
		return "Student [StdEmail=" + StdEmail + ", StdPassword=" + StdPassword + ", StdName=" + StdName + "]";
	}
	
	
	
	
}
