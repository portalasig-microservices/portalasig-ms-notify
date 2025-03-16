package com.portalasig.ms.notify.service;

import com.portalasig.ms.notify.dto.EmailRequest;
import com.portalasig.ms.notify.producer.EmailEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailService {

    private final EmailEventProducer emailEventProducer;

    public void publishEmailEvent(EmailRequest request) {
        emailEventProducer.publish(request);
    }
}
