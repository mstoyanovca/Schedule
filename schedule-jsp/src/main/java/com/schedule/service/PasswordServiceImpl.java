package com.schedule.service;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service("passwordService")
public class PasswordServiceImpl implements PasswordService {

	private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
	private static final String DIGITS = "0123456789";
	private static final String SPECIAL_CHARS = "!@#$%^&*()_=+-{}:|<>?,.";
	private static final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_=+-{}:|<>?,.]).{6,20})";
	private Pattern pattern = Pattern.compile(PASSWORD_REGEX);
	private Matcher matcher;

	public char[] generate(int minLength, int maxLength, int upperCase, int lowerCase, int digits, int specialChars) {
		if (minLength > maxLength)
			throw new IllegalArgumentException("minLength > maxLength");
		if ((upperCase + lowerCase + digits + specialChars) > minLength)
			throw new IllegalArgumentException("minLength < (upperCase + lowerCase + digits + specialChars)");
		SecureRandom rnd = new SecureRandom();
		int length = rnd.nextInt(maxLength - minLength + 1) + minLength;
		char[] password = new char[length];
		int index = 0;
		for (int i = 0; i < upperCase; i++) {
			index = getNextIndex(rnd, length, password);
			password[index] = UPPER_CASE.charAt(rnd.nextInt(LOWER_CASE.length()));
		}
		for (int i = 0; i < digits; i++) {
			index = getNextIndex(rnd, length, password);
			password[index] = DIGITS.charAt(rnd.nextInt(DIGITS.length()));
		}
		for (int i = 0; i < specialChars; i++) {
			index = getNextIndex(rnd, length, password);
			password[index] = SPECIAL_CHARS.charAt(rnd.nextInt(SPECIAL_CHARS.length()));
		}
		for (int i = 0; i < length; i++) {
			if (password[i] == 0) {
				password[i] = LOWER_CASE.charAt(rnd.nextInt(LOWER_CASE.length()));
			}
		}
		return password;
	}

	public boolean validate(final String password) {
		matcher = pattern.matcher(password);
		return matcher.matches();
	}

	private static int getNextIndex(SecureRandom rnd, int len, char[] pswd) {
		int index = rnd.nextInt(len);
		while (pswd[index = rnd.nextInt(len)] != 0) {
			;
		}
		return index;
	}
}
