package com.portalasig.ms.notify.producer;

import com.portalasig.ms.notify.domain.event.EmailEvent;
import com.portalasig.ms.notify.dto.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Component responsible for publishing {@link EmailEvent} to the Spring application context.
 */
@Component
@RequiredArgsConstructor
public class EmailEventProducer {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Publishes an email event based on the provided {@link EmailRequest}.
     *
     * @param request the email request containing recipient, subject, template, and configuration
     */
    public void publish(EmailRequest request) {
        EmailEvent emailEvent = new EmailEvent(
                this,
                request.getEmailTo(),
                request.getSubject(),
                request.getTemplate(),
                request.getTemplateConfiguration()
        );
        eventPublisher.publishEvent(emailEvent);
    }
}