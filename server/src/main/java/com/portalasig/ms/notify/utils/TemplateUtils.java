package com.portalasig.ms.notify.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class TemplateUtils {

    public static String loadResource(Resource resource) {
        try {
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource", e);
        }
    }

    public static Object getOrNull(JsonNode templateConfiguration, String title) {
        return templateConfiguration.has(title) ? templateConfiguration.get(title).asText() : null;
    }
}
