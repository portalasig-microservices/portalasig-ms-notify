package com.portalasig.ms.notify.listener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.portalasig.ms.commons.test.AbstractMysqlIntegrationTest;
import com.portalasig.ms.notify.constant.EmailTemplate;
import com.portalasig.ms.notify.dto.EmailRequest;
import com.portalasig.ms.notify.producer.EmailEventProducer;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class NotifyKafkaEmailListenerIntegrationTest extends AbstractMysqlIntegrationTest {

  @Autowired private NotifyKafkaEmailListener kafkaEmailListener;

  @MockBean private EmailEventProducer emailEventProducer;

  @Test
  void validSnakeCasePayloadShouldBeRepublishedAsEmailEvent() {
    String payload =
        "{\"email_to\": \"estudiante@portalasig.ucv.ve\", \"subject\": \"Aviso\","
            + " \"template\": \"SIMPLE_MESSAGE\","
            + " \"template_configuration\": {\"message\": \"hola\", \"by\": \"site\"}}";

    kafkaEmailListener.onMessage(payload);

    ArgumentCaptor<EmailRequest> captor = ArgumentCaptor.forClass(EmailRequest.class);
    verify(emailEventProducer).publish(captor.capture());
    EmailRequest request = captor.getValue();
    assertThat(request.getEmailTo()).isEqualTo("estudiante@portalasig.ucv.ve");
    assertThat(request.getSubject()).isEqualTo("Aviso");
    assertThat(request.getTemplate()).isEqualTo(EmailTemplate.SIMPLE_MESSAGE);
  }

  @Test
  void validCamelCasePayloadShouldBeRepublishedAsEmailEvent() {
    String payload =
        "{\"emailTo\": \"profesor@portalasig.ucv.ve\", \"subject\": \"Aviso\","
            + " \"template\": \"APP_NOTIFICATION\","
            + " \"templateConfiguration\": {\"title\": \"t\"}}";

    kafkaEmailListener.onMessage(payload);

    ArgumentCaptor<EmailRequest> captor = ArgumentCaptor.forClass(EmailRequest.class);
    verify(emailEventProducer).publish(captor.capture());
    assertThat(captor.getValue().getEmailTo()).isEqualTo("profesor@portalasig.ucv.ve");
    assertThat(captor.getValue().getTemplate()).isEqualTo(EmailTemplate.APP_NOTIFICATION);
  }

  @Test
  void payloadWithMissingRecipientShouldBeDiscarded() {
    String payload =
        "{\"subject\": \"Aviso\", \"template\": \"SIMPLE_MESSAGE\","
            + " \"template_configuration\": {\"message\": \"m\", \"by\": \"b\"}}";

    kafkaEmailListener.onMessage(payload);

    verify(emailEventProducer, never()).publish(any());
  }

  @Test
  void payloadWithBlankSubjectShouldBeDiscarded() {
    String payload =
        "{\"email_to\": \"a@b.com\", \"subject\": \"  \", \"template\": \"SIMPLE_MESSAGE\","
            + " \"template_configuration\": {\"message\": \"m\", \"by\": \"b\"}}";

    kafkaEmailListener.onMessage(payload);

    verify(emailEventProducer, never()).publish(any());
  }

  @Test
  void payloadWithUnknownTemplateShouldBeDiscarded() {
    String payload =
        "{\"email_to\": \"a@b.com\", \"subject\": \"s\", \"template\": \"NO_EXISTE\","
            + " \"template_configuration\": {\"message\": \"m\"}}";

    kafkaEmailListener.onMessage(payload);

    verify(emailEventProducer, never()).publish(any());
  }

  @Test
  void payloadWithoutTemplateConfigurationShouldBeDiscarded() {
    String payload = "{\"email_to\": \"a@b.com\", \"subject\": \"s\", \"template\": \"SIMPLE_MESSAGE\"}";

    kafkaEmailListener.onMessage(payload);

    verify(emailEventProducer, never()).publish(any());
  }

  @Test
  void malformedJsonShouldBeDiscardedWithoutFailing() {
    kafkaEmailListener.onMessage("esto no es json");

    verify(emailEventProducer, never()).publish(any());
  }
}
