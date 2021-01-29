package app.service;

import javax.mail.MessagingException;

public interface EmailService {
    public void sendMail(String to, String subject, String body);
    public void sendMaiConfirmation(String to, String subject, String body, String link) throws MessagingException;
}
