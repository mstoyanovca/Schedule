package com.schedule.service;

public interface PasswordService {

	public boolean validate(String password);

	public char[] generate(int minLength, int maxLength, int upperCase, int lowerCase, int digits, int specialChars);
}
