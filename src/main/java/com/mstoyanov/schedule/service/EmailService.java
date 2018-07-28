package com.mstoyanov.schedule.service;

public interface EmailService {

	void sendActivationToken(String email, String name, String token);

	void sendPwdResetToken(String email, String name, String token);
}