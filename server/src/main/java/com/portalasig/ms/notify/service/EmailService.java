package com.portalasig.ms.notify.service;

import com.portalasig.ms.notify.dto.Email;
import com.portalasig.ms.notify.dto.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailService {

    // Spring autowires if the properties for it are set in the configuration
    private final JavaMailSender mailSender;
    private final TemplateService templateService;
    @Value("${spring.mail.username}")
    private String emailDispatcher;

    public Email sendEmail(EmailRequest request) {
        String htmlContent = templateService.createContentFromTemplate(
                request.getTemplate(),
                request.getTemplateConfiguration()
        );
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress(emailDispatcher));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(request.getEmailTo()));
            message.setContent(htmlContent, "text/html; charset=utf-8");
            log.info("Sending email to: {}", request.getEmailTo());
            mailSender.send(message);
        } catch (MessagingException exception) {
            log.error("Error sending email", exception);
        }
        return Email.builder().emailTo(request.getEmailTo()).sentAt(Instant.now()).build();
    }
}
