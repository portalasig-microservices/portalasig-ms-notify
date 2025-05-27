package com.portalasig.ms.notify.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portalasig.ms.notify.client.EmailNotifyClient;
import com.portalasig.ms.notify.client.EmailNotifyClientV1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Auto-configuration for the EmailNotifyClient used to send email notifications.
 */
@Configuration
public class EmailNotifyClientAutoConfiguration {

    /**
     * Creates the primary {@link EmailNotifyClient} bean for sending application emails using the v1 implementation.
     *
     * @param baseUrl      the base URL of the notification microservice
     * @param webClient    the shared {@link WebClient} instance
     * @param objectMapper the {@link ObjectMapper} used to serialize email requests
     * @return a configured instance of {@link EmailNotifyClientV1}
     */
    @Primary
    @Bean(name = "emailNotifyClientV1")
    public EmailNotifyClient getEmailNotifyClientV1(
            @Value("${portalasig.notify.ms.v1.url}") String baseUrl,
            WebClient webClient,
            ObjectMapper objectMapper) {
        return new EmailNotifyClientV1(webClient, baseUrl, objectMapper);
    }
}