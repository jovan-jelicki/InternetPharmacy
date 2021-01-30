package app.controller.impl;

import app.dto.EmailDTO;
import app.dto.UserPasswordDTO;
import app.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping(value = "api/email")

public class EmailControllerImpl {
    private final EmailService emailService;

    public EmailControllerImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @PutMapping(value = "/send")
    public ResponseEntity<Void> sendMail(@RequestBody EmailDTO emailParams) {
        try {
            emailService.sendMail(emailParams.getTo(), emailParams.getSubject(), emailParams.getBody());
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/confirm")
    public ResponseEntity<Void> sendConfirmMail(@RequestBody EmailDTO emailParams) {
        try {
            emailService.sendMaiConfirmation(emailParams.getTo(), emailParams.getSubject(), emailParams.getBody(),emailParams.getLink());
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}