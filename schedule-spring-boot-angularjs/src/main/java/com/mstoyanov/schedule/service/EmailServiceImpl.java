package com.mstoyanov.schedule.service;

import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	private JavaMailSender mailSender;
	private TemplateEngine templateEngine;
	private Environment env;
	// private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine, Environment env) {
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
		this.env = env;
	}

	@Override
	public void sendActivationToken(String email, String name, String token) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(email);
				message.setFrom(env.getProperty("spring.mail.username"));
				message.setSubject("Account activation request from Music Lessons Schedule");
				Context ctx = new Context();
				ctx.setVariable("name", name);
				ctx.setVariable("token", token);
				String content = templateEngine.process("activation-email", ctx);
				message.setText(content, true);
			}
		};
		// logger.info("Sending account activation email to " + email);
		mailSender.send(preparator);
	}

	@Override
	public void sendPwdResetToken(String email, String name, String token) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(email);
				message.setFrom(env.getProperty("spring.mail.username"));
				message.setSubject("Password reset request from Music Lessons Schedule");
				Context ctx = new Context();
				ctx.setVariable("name", name);
				ctx.setVariable("token", token);
				String content = templateEngine.process("reset-password-email", ctx);
				message.setText(content, true);
			}
		};
		// logger.info("Sending password reset email to " + email);
		mailSender.send(preparator);
	}
}
