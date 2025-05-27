package com.portalasig.ms.notify.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portalasig.ms.commons.rest.exception.BadRequestException;
import com.portalasig.ms.notify.constant.EmailTemplate;
import com.portalasig.ms.notify.utils.TemplateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service for processing email templates based on {@link EmailTemplate} and
 * configuration data passed as JSON.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class TemplateService {

    private final TemplateEngine templateEngine;
    private final ObjectMapper objectMapper;

    @Value("classpath:static/css/style.css")
    private Resource baseCss;

    private final Map<EmailTemplate, Function<JsonNode, Map<String, Object>>> TEMPLATE_CONFIGURATION = Map.of(
            EmailTemplate.APP_NOTIFICATION, (JsonNode templateConfiguration) -> {
                Map<String, Object> templateConfigurationMap = new HashMap<>();
                templateConfigurationMap.put("title", TemplateUtils.getOrNull(templateConfiguration, "title"));
                templateConfigurationMap.put("target", TemplateUtils.getOrNull(templateConfiguration, "target"));
                templateConfigurationMap.put("primary_body", TemplateUtils.getOrNull(templateConfiguration, "primary_body"));
                templateConfigurationMap.put("secondary_body", TemplateUtils.getOrNull(templateConfiguration, "secondary_body"));
                templateConfigurationMap.put("url", TemplateUtils.getOrNull(templateConfiguration, "url"));
                templateConfigurationMap.put("url_label", TemplateUtils.getOrNull(templateConfiguration, "url_label"));
                templateConfigurationMap.put("closing_message", TemplateUtils.getOrNull(templateConfiguration, "closing_message"));
                templateConfigurationMap.put("external_css", TemplateUtils.loadResource(baseCss));
                return templateConfigurationMap;
            },
            EmailTemplate.SIMPLE_MESSAGE, (JsonNode templateConfiguration) -> {
                Map<String, Object> templateConfigurationMap = new HashMap<>();
                templateConfigurationMap.put("message", TemplateUtils.getOrNull(templateConfiguration, "message"));
                templateConfigurationMap.put("by", TemplateUtils.getOrNull(templateConfiguration, "by"));
                templateConfigurationMap.put("external_css", TemplateUtils.loadResource(baseCss));
                return templateConfigurationMap;
            }
    );

    /**
     * Processes the given {@link EmailTemplate} with the provided template configuration.
     *
     * @param template              the email template enum
     * @param templateConfiguration the configuration object for the template
     * @return the rendered template as a String
     * @throws BadRequestException if the template is invalid or configuration is null
     */
    public String processEmailTemplate(EmailTemplate template, Object templateConfiguration) {
        JsonNode templateConfigurationJson = getTemplateConfigurationJson(templateConfiguration);
        if (!template.isValid()) {
            throw new BadRequestException("Template not found");
        }

        if (templateConfigurationJson == null) {
            throw new BadRequestException("Invalid template_configuration");
        }

        Context context = new Context();
        Map<String, Object> templateConfigurationMap = TEMPLATE_CONFIGURATION
                .get(template)
                .apply(templateConfigurationJson);
        context.setVariables(templateConfigurationMap);
        return templateEngine.process(template.getTemplateName(), context);
    }

    /**
     * Converts the given object to a {@link JsonNode} using Jackson.
     *
     * @param templateConfiguration the configuration object to convert
     * @return the {@link JsonNode} representation of the object or null if parsing fails
     */
    private JsonNode getTemplateConfigurationJson(Object templateConfiguration) {
        try {
            return objectMapper.valueToTree(templateConfiguration);
        } catch (Exception e) {
            log.error("Error parsing template_configuration={}", templateConfiguration, e);
            return null;
        }
    }
}