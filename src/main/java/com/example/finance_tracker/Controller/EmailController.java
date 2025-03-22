package com.example.finance_tracker.Controller;
import com.example.finance_tracker.Service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam String to,
                            @RequestParam String subject,
                            @RequestParam String body) {
        emailService.sendEmail(to, subject, body);
        return "Email sent successfully!";
    }
}
