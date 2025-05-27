package com.portalasig.ms.notify.service;

import com.portalasig.ms.notify.dto.EmailRequest;
import com.portalasig.ms.notify.producer.EmailEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service responsible for handling email-related operations,
 * such as publishing email events to a message broker or downstream system.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class EmailService {

    private final EmailEventProducer emailEventProducer;

    /**
     * Publishes an email event to the configured producer.
     *
     * @param request the email request to be published
     */
    public void publishEmailEvent(EmailRequest request) {
        emailEventProducer.publish(request);
    }
}