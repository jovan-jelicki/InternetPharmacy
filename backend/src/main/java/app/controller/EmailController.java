package app.controller;

public interface EmailController {
    public void sendMail(String to, String subject, String body);
    public void sendMaiConfirmation(String to, String subject, String body, String link);

}
