package com.portalasig.ms.notify.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for handling email templates and resources.
 */
public final class TemplateUtils {

    private TemplateUtils() {
        // Utility class, no instances allowed
    }

    /**
     * Loads the contents of a {@link Resource} into a {@link String} using UTF-8 encoding.
     *
     * @param resource the resource to load
     * @return the content of the resource as a string
     * @throws RuntimeException if the resource cannot be read
     */
    public static String loadResource(Resource resource) {
        try {
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource", e);
        }
    }

    /**
     * Safely retrieves a string value from a {@link JsonNode} by its field name.
     *
     * @param templateConfiguration the JSON node to query
     * @param title                 the name of the field to retrieve
     * @return the string value of the field, or {@code null} if not present
     */
    public static Object getOrNull(JsonNode templateConfiguration, String title) {
        return templateConfiguration.has(title) ? templateConfiguration.get(title).asText() : null;
    }
}