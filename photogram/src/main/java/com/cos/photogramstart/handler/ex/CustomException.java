package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private Map<String, String> errorMap;
	
	public CustomException(String message) {
		super(message); 
	}

}
