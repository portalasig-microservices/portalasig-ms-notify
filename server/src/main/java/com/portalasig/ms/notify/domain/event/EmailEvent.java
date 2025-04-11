package com.portalasig.ms.notify.domain.event;

import com.portalasig.ms.notify.constant.EmailTemplate;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailEvent extends ApplicationEvent {

    private final String emailTo;

    private final String subject;

    private final EmailTemplate template;

    private final Object templateConfiguration;

    public EmailEvent(
            Object source,
            String emailTo,
            String subject,
            EmailTemplate template,
            Object templateConfiguration
    ) {
        super(source);
        this.emailTo = emailTo;
        this.subject = subject;
        this.template = template;
        this.templateConfiguration = templateConfiguration;
    }
}