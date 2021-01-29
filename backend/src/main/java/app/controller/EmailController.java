package app.controller;

public interface EmailController {
    public void sendMail(String to, String subject, String body);
}
