package com.portalasig.ms.notify.domain.event;

import com.portalasig.ms.notify.constant.EmailTemplate;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event representing an email sending request. Contains information about the recipient, subject,
 * template, and configuration for rendering the email.
 */
@Getter
public class EmailEvent extends ApplicationEvent {

    private final String emailTo;
    private final String subject;
    private final EmailTemplate template;
    private final Object templateConfiguration;

    /**
     * Constructs a new {@link EmailEvent}.
     *
     * @param source                the object on which the event initially occurred (never {@code null})
     * @param emailTo               recipient email address
     * @param subject               email subject
     * @param template              the email template to use
     * @param templateConfiguration dynamic configuration data for rendering the template
     */
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