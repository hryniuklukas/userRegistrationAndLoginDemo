package com.github.hryniuklukas.userRegistrationAndLoginDemo.registration.email.services;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Slf4j

@Component
public class ConfirmationMailService implements EmailService {
  @Autowired private JavaMailSender emailSender;
  Logger logger = LoggerFactory.getLogger(ConfirmationMailService.class);

  @Async
  public void sendMail(String to, String email) throws MessagingException {
    try {
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message);

      helper.setTo(to);
      helper.setFrom("confirmation@hryniuklukas.com");
      helper.setSubject("Confirm your registration");
      helper.setText(email, true);

      emailSender.send(message);
    } catch (MessagingException e) {
      logger.error("Sending an email failed", e);
      throw new IllegalStateException("Failed to send email");
    }
  }
}
