package com.esp.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SMTPMailSender {

	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * To send the mail using SMTP
	 * 
	 * @param to - Email id to send mail
	 * @param subject 
	 * @param body
	 * @throws MessagingException
	 */
	public void send(String to, String subject, String body) throws MessagingException {
		// setting the new messege
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;

		// setting the subject and the body
		helper = new MimeMessageHelper(message, true);
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body, true);

		// sending the mail
		javaMailSender.send(message);

	}
}
