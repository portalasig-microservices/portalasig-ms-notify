package com.portalasig.ms.notify.rest;

import com.portalasig.ms.commons.constants.RestConstants;
import com.portalasig.ms.notify.constant.NotifyRestConstants;
import com.portalasig.ms.notify.dto.Email;
import com.portalasig.ms.notify.dto.EmailRequest;
import com.portalasig.ms.notify.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for handling email dispatch requests.
 */
@RestController
@RequestMapping(RestConstants.VERSION_ONE + NotifyRestConstants.EMAIL)
@RequiredArgsConstructor
@Api(value = "Email sender controller", tags = {"email", "sender"})
@Slf4j
public class EmailController {

    private final EmailService emailService;

    /**
     * Receives and processes email sending requests.
     *
     * @param request the email request containing recipient, template, and configuration
     */
    @ApiOperation(value = "Send email", response = Email.class)
    @PostMapping
    public void sendEmail(
            @Valid @RequestBody EmailRequest request
    ) {
        emailService.publishEmailEvent(request);
    }
}