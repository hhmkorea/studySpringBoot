package com.cos.blog.test;

public class People {
	private int hugryState = 50; //100, 변수는 private 
	
	public void eat() {
		hugryState += 10;
	}
	
	public static void main(String[] args) {
		People p = new People();
		//p.hugryState = 100; // 마법 
		p.eat(); // 함수로 접근, 함수는 public
	}
}
