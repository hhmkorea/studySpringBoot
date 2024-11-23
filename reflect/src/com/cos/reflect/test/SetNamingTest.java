package com.cos.reflect.test;

import org.junit.Test;

public class SetNamingTest {
	
	@Test
	public void changeToSetterFromKey() { // 키값을 세터로 바꾸기. 
		String key = "username";
		
		String firstKey = "set";
		String upperKey = key.substring(0,1).toUpperCase();
		String remainKey = key.substring(1);
		
		System.out.println(firstKey);
		System.out.println(upperKey);
		System.out.println(remainKey);
		
		String result = firstKey + upperKey + remainKey;
		System.out.println(result);
	}
}
