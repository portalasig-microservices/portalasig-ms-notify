package com.portalasig.ms.notify.producer;

import com.portalasig.ms.notify.domain.event.EmailEvent;
import com.portalasig.ms.notify.dto.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailEventProducer {

    private final ApplicationEventPublisher eventPublisher;

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