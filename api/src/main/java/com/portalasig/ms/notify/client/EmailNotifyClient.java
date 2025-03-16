package com.portalasig.ms.notify.client;

import com.portalasig.ms.notify.dto.EmailRequest;
import reactor.core.publisher.Mono;

public interface EmailNotifyClient {

    Mono<Void> sendApplicationEmail(EmailRequest request);
}
