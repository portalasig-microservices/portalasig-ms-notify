package com.portalasig.ms.notify.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portalasig.ms.notify.client.EmailNotifyClient;
import com.portalasig.ms.notify.client.EmailNotifyClientV1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class EmailNotifyClientAutoConfiguration {

    @Primary
    @Bean(name = "emailNotifyClientV1")
    public EmailNotifyClient getEmailNotifyClientV1(
            @Value("${portalasig.notify.ms.v1.url}") String baseUrl, WebClient webClient, ObjectMapper objectMapper) {
        return new EmailNotifyClientV1(webClient, baseUrl, objectMapper);
    }
}
