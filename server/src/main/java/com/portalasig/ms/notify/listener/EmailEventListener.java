package com.portalasig.ms.notify.listener;

import com.portalasig.ms.notify.domain.event.EmailEvent;
import com.portalasig.ms.notify.dto.Email;
import com.portalasig.ms.notify.dto.EmailRequest;
import com.portalasig.ms.notify.mapper.EmailMapper;
import com.portalasig.ms.notify.service.TemplateService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailEventListener {

    private final EmailMapper emailMapper;
    private final TemplateService templateService;
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailDispatcher;
    public final String TEXT_HTML_CHARSET_UTF8 = "text/html; charset=utf-8";

    @Async
    @EventListener
    public void onEvent(EmailEvent event) {
        log.info("Processing email event={}", event);
        EmailRequest request = emailMapper.toRequest(event);
        Email email = sendEmail(request);
        // TODO: Store the email status information and the request in the database
    }

    public Email sendEmail(EmailRequest request) {
        String htmlContent = templateService.processEmailTemplate(
                request.getTemplate(),
                request.getTemplateConfiguration()
        );
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress(emailDispatcher));
            message.setSubject(request.getSubject());
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(request.getEmailTo()));
            message.setContent(htmlContent, TEXT_HTML_CHARSET_UTF8);
            log.info("Sending email to: {}", request.getEmailTo());
            mailSender.send(message);
        } catch (MessagingException exception) {
            log.error("Error sending email", exception);
        }
        log.debug("Email has been successfully sent to: {}", request.getEmailTo());
        return emailMapper.toDto(request, Instant.now());
    }
}