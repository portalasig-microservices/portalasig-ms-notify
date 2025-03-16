package com.portalasig.ms.notify.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portalasig.ms.commons.rest.exception.SystemErrorException;
import com.portalasig.ms.notify.constant.NotifyRestConstants;
import com.portalasig.ms.notify.dto.EmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class EmailNotifyClientV1 implements EmailNotifyClient {

    private final WebClient webClient;
    private final String baseUrl;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> sendApplicationEmail(EmailRequest request) {
        try {
            String body = objectMapper.writeValueAsString(request);
            return webClient
                    .post()
                    .uri(baseUrl + NotifyRestConstants.EMAIL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Void.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new SystemErrorException("error while processing request");
        }
    }
}
