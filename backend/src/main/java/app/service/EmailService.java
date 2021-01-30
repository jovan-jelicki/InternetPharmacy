package app.service;

import javax.mail.MessagingException;

public interface EmailService {
     void sendMail(String to, String subject, String body);
     void sendMaiConfirmation(String to, String subject, String body, String link) throws MessagingException;
}
