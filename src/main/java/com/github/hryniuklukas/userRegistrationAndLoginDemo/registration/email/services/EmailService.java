package com.github.hryniuklukas.userRegistrationAndLoginDemo.registration.email.services;

import javax.mail.MessagingException;

public interface EmailService {
    void sendMail(String to, String email) throws MessagingException;
}
