package com.schedule.email;

import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import com.schedule.model.User;

@Service("emailService")
public class VelocityEmailServiceImpl implements VelocityEmailService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	// private static final Logger logger = Logger.getLogger(VelocityEmailServiceImpl.class);

	public void sendEmailConfirmationToken(final User user) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(user.getEmail());
				message.setFrom("${mail.username}");
				message.setSubject("Email confirmation request");
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("user", user);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
						"email-confirmation.vm", "UTF-8", model);
				message.setText(text, true);
			}
		};
		// logger.info("Sending email to " + user.getEmail());
		this.mailSender.send(preparator);
	}
	
	public void sendPasswordResetToken(final User user) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(user.getEmail());
				message.setFrom("${mail.username}");
				message.setSubject("Password reset");
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("user", user);
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, 
						"password-reset.vm", "UTF-8", model);
				message.setText(text, true);
			}
		};
		// logger.info("Sending email to " + user.getEmail());
		this.mailSender.send(preparator);
	}
}
