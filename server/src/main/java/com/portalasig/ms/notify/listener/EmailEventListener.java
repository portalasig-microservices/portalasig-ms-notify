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
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Event listener that processes {@link EmailEvent} and sends emails asynchronously.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class EmailEventListener {

    private final EmailMapper emailMapper;
    private final TemplateService templateService;
    private final JavaMailSender mailSender;

    @Value("${portalasig.notify.email.debug.enabled:true}")
    private final boolean isEmailDebugEnabled;

    @Value("${portalasig.notify.email.debug.recipient}")
    private final String emailDebugRecipient;

    @Value("${spring.mail.username}")
    private String emailDispatcher;

    /**
     * The MIME type used for HTML email content.
     */
    public static final String TEXT_HTML_CHARSET_UTF8 = "text/html; charset=utf-8";

    /**
     * Handles {@link EmailEvent} asynchronously and triggers the email sending process.
     *
     * @param event the email event to process
     */
    @Async
    @EventListener
    public void onEvent(EmailEvent event) {
        log.info("Processing email event={}", event);
        EmailRequest request = emailMapper.toRequest(event);
        Email email = sendEmail(request);
        // TODO: Store the email status information and the request in the database
    }

    /**
     * Sends an email based on the given request using a templated HTML message.
     *
     * @param request the email request containing recipient, subject, template, etc.
     * @return the {@link Email} object representing the sent email metadata
     */
    public Email sendEmail(EmailRequest request) {
        String htmlContent = templateService.processEmailTemplate(
                request.getTemplate(),
                request.getTemplateConfiguration()
        );
        MimeMessage message = mailSender.createMimeMessage();
        String recipient = request.getEmailTo();
        if (isEmailDebugEnabled) {
            recipient = emailDebugRecipient;
        }
        try {
            message.setFrom(new InternetAddress(emailDispatcher));
            message.setSubject(request.getSubject());
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(recipient));
            message.setContent(htmlContent, TEXT_HTML_CHARSET_UTF8);
            log.info("Sending email via SMTP: from={}, to={}, subject={}, debug_mode={}, effective_to={}",
                    emailDispatcher, request.getEmailTo(), request.getSubject(), isEmailDebugEnabled, recipient);
            mailSender.send(message);
            log.info("Email sent successfully to: {} (effective)", recipient);
        } catch (MessagingException | MailException exception) {
            log.error("Error sending email via SMTP: from={}, to={}, subject={}", emailDispatcher, recipient, request.getSubject(), exception);
        }
        return emailMapper.toDto(request, Instant.now());
    }
}