package com.example.HopeConnect.Controllers;
import com.example.HopeConnect.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String content) {
        try {
            emailService.sendEmail(to, subject, content);
            return "Email sent successfully!";
        } catch (IOException e) {
            return "Failed to send email: " + e.getMessage();
        }
    }
}
