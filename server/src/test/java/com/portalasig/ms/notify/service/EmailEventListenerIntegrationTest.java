package com.portalasig.ms.notify.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.portalasig.ms.commons.test.AbstractMysqlIntegrationTest;
import com.portalasig.ms.notify.constant.EmailTemplate;
import com.portalasig.ms.notify.dto.Email;
import com.portalasig.ms.notify.dto.EmailRequest;
import com.portalasig.ms.notify.listener.EmailEventListener;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

class EmailEventListenerIntegrationTest extends AbstractMysqlIntegrationTest {

  @Autowired private EmailService emailService;

  @Autowired private EmailEventListener emailEventListener;

  @MockBean private JavaMailSender mailSender;

  private MimeMessage mimeMessage;

  private static EmailRequest baseRequest() {
    return EmailRequest.builder()
        .emailTo("estudiante@portalasig.ucv.ve")
        .subject("Recuperacion de clave")
        .template(EmailTemplate.APP_NOTIFICATION)
        .templateConfiguration(
            Map.of(
                "title", "Recupera tu clave",
                "target", "Hola, estudiante",
                "primary_body", "Sigue el enlace.",
                "secondary_body", "Ignora si no fuiste tu.",
                "url", "http://localhost:8080/reiniciar",
                "url_label", "Recuperar",
                "closing_message", "Vence en 5 minutos"))
        .build();
  }

  @BeforeEach
  void setUpMimeMessage() {
    mimeMessage = new MimeMessage((jakarta.mail.Session) null);
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
  }

  @Test
  void sendEmailShouldRenderTemplateAndDispatchToDebugRecipient() throws Exception {
    Email email = emailEventListener.sendEmail(baseRequest());

    assertThat(email.getEmailTo()).isEqualTo("estudiante@portalasig.ucv.ve");
    assertThat(email.getSubject()).isEqualTo("Recuperacion de clave");
    assertThat(email.getSentAt()).isNotNull();

    ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);
    verify(mailSender).send(captor.capture());
    MimeMessage sent = captor.getValue();
    // debug mode redirects every email to the configured debug recipient
    assertThat(sent.getRecipients(MimeMessage.RecipientType.TO)[0].toString())
        .isEqualTo("debug-recipient@portalasig.ucv.ve");
    assertThat(sent.getSubject()).isEqualTo("Recuperacion de clave");
    assertThat(sent.getFrom()[0].toString()).isEqualTo("dispatcher@portalasig.ucv.ve");
    assertThat(sent.getContent().toString()).contains("Recupera tu clave");
  }

  @Test
  void publishEmailEventShouldTriggerAsyncDispatch() {
    emailService.publishEmailEvent(baseRequest());

    verify(mailSender, timeout(5000)).send(any(MimeMessage.class));
  }
}
