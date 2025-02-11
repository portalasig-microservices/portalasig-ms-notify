package com.portalasig.ms.notify.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.portalasig.ms.commons.rest.exception.BadRequestException;
import com.portalasig.ms.commons.rest.exception.SystemErrorException;
import com.portalasig.ms.notify.constant.EmailTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
@Slf4j
public class TemplateService {

    @Value("classpath:templates/email-template.html")
    private Resource testEmailTemplate;

    public String createContentFromTemplate(EmailTemplate template, JsonNode templateConfiguration) {
        switch (template) {
            case TEST_TEMPLATE:
                return createTestTemplate(templateConfiguration);
            default:
                throw new BadRequestException("Template not found");
        }
    }

    private String createTestTemplate(JsonNode templateConfiguration) {
        try {
            String html = new String(testEmailTemplate.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            return html
                    .replace("{name}", templateConfiguration.get("name").asText())
                    .replace("{product}", templateConfiguration.get("product").asText());
        } catch (IOException exception) {
            throw new SystemErrorException("Error creating email template");
        }
    }
}
