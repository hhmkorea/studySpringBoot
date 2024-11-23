package com.cos.reflect.model;

public class User {
	private int id;
	private String username;
	private String password;
	private String email;

	@Override
	public String toString() { // User Object 확인하기 편해짐. 참조형 변수(reference variable)를 Sysout하면 toString이 자동 호출되어 보기 편해짐.
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + "]";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
		
}
