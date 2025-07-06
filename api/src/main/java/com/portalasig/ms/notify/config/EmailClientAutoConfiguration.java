package com.portalasig.ms.notify.config;

import com.portalasig.ms.notify.operation.EmailOperations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * Auto-configuration for creating {@link EmailOperations} clients
 * with different authentication mechanisms.
 */
@Configuration
public class EmailClientAutoConfiguration {

    /**
     * Creates an {@link EmailOperations} client using client credentials authentication.
     *
     * @param baseUrl   the base URL for the email service
     * @param webClient the pre-configured WebClient bean for client credentials
     * @return a proxy implementation of {@link EmailOperations}
     */
    @Bean(name = "clientCredentialsEmailClientV1")
    public EmailOperations clientCredentialsEmailClientV1(
            @Value("${portalasig.notify.ms.url}") String baseUrl,
            @Qualifier("clientCredentialsWebClient") WebClient webClient) {
        WebClient client = webClient.mutate().baseUrl(baseUrl).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.create(client)).build();
        return factory.createClient(EmailOperations.class);
    }

    /**
     * Creates an {@link EmailOperations} client using JWT token relay authentication.
     *
     * @param baseUrl   the base URL for the email service
     * @param webClient the pre-configured WebClient bean for JWT token relay
     * @return a proxy implementation of {@link EmailOperations}
     */
    @Bean(name = "tokenRelayEmailClientV1")
    public EmailOperations tokenRelayEmailClientV1(
            @Value("${portalasig.notify.ms.url}") String baseUrl,
            @Qualifier("jwtTokenWebClient") WebClient webClient) {
        WebClient client = webClient.mutate().baseUrl(baseUrl).build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.create(client)).build();
        return factory.createClient(EmailOperations.class);
    }
}