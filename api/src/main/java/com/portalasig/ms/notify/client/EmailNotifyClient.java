package com.portalasig.ms.notify.client;

import com.portalasig.ms.notify.dto.EmailRequest;
import reactor.core.publisher.Mono;

/**
 * Client interface for sending email notifications.
 */
public interface EmailNotifyClient {

    /**
     * Sends an application email based on the given request.
     *
     * @param request the email request payload
     * @return a Mono indicating completion
     */
    Mono<Void> sendApplicationEmail(EmailRequest request);
}