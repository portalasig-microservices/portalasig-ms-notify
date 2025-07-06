package com.portalasig.ms.notify.rest;

import com.portalasig.ms.notify.dto.EmailRequest;
import com.portalasig.ms.notify.operation.EmailOperations;
import com.portalasig.ms.notify.service.EmailService;
import io.swagger.annotations.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for handling email dispatch requests.
 */
@RestController
@RequiredArgsConstructor
@Api(value = "Email sender controller", tags = {"email", "sender"})
public class EmailController implements EmailOperations {

    private final EmailService emailService;

    @Override
    public void sendEmail(@Valid @RequestBody EmailRequest request) {
        emailService.publishEmailEvent(request);
    }
}