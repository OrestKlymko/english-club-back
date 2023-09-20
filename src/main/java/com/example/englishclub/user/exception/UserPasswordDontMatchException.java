package com.example.englishclub.user.exception;

public class UserPasswordDontMatchException extends Exception{
	public UserPasswordDontMatchException(String message) {
		super(message);
	}
}
