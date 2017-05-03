package com.schedule.email;

import com.schedule.model.User;

public interface VelocityEmailService {

	public void sendEmailConfirmationToken(final User user);
	
	public void sendPasswordResetToken(final User user);
}