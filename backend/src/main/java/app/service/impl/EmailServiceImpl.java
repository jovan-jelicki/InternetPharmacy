package app.service.impl;

import app.service.EmailService;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Override
    public void sendMaiConfirmation(String to, String subject, String body, String link) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        Multipart multiPart = new MimeMultipart("alternative");

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(body, "utf-8");

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(link, "text/html; charset=utf-8");

        multiPart.addBodyPart(textPart); // <-- first
        multiPart.addBodyPart(htmlPart); // <-- second

        message.setContent(multiPart);
        message.setRecipients(Message.RecipientType.TO, to);

        message.setSubject(subject);
        mailSender.send(message);


         /*
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setText(body, link);
            helper.setTo(to);
            helper.setSubject(subject);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
*/
        mailSender.send(message);

    }


}
