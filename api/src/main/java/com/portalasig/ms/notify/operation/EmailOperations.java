package com.portalasig.ms.notify.operation;

import com.portalasig.ms.notify.constant.RestPaths;
import com.portalasig.ms.notify.dto.EmailRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * Interface for email-related operations.
 * Defines REST endpoints for sending emails.
 */
@HttpExchange(RestPaths.Email.BASE)
public interface EmailOperations {

    /**
     * Sends an email using the provided request data.
     *
     * @param request the email request containing recipient, subject, and message body
     */
    @PostExchange
    void sendEmail(@Valid @RequestBody EmailRequest request);
}