package com.portalasig.ms.notify.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.portalasig.ms.commons.rest.exception.BadRequestException;
import com.portalasig.ms.commons.test.AbstractMysqlIntegrationTest;
import com.portalasig.ms.notify.constant.EmailTemplate;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TemplateServiceIntegrationTest extends AbstractMysqlIntegrationTest {

  @Autowired private TemplateService templateService;

  @Test
  void appNotificationTemplateShouldRenderAllSections() {
    Map<String, Object> configuration =
        Map.of(
            "title", "Alerta P1",
            "target", "Hola, administrador",
            "primary_body", "El servicio Site esta caido.",
            "secondary_body", "Se recomienda reiniciar.",
            "url", "http://localhost:8080/alertas",
            "url_label", "Ver alerta",
            "closing_message", "Mensaje automatico de PAIMA");

    String html =
        templateService.processEmailTemplate(EmailTemplate.APP_NOTIFICATION, configuration);

    assertThat(html)
        .contains("Alerta P1")
        .contains("Hola, administrador")
        .contains("El servicio Site esta caido.")
        .contains("http://localhost:8080/alertas")
        .contains("Mensaje automatico de PAIMA");
  }

  @Test
  void simpleMessageTemplateShouldRenderMessageAndAuthor() {
    Map<String, Object> configuration = Map.of("message", "Bienvenido al portal", "by", "UAA");

    String html = templateService.processEmailTemplate(EmailTemplate.SIMPLE_MESSAGE, configuration);

    assertThat(html).contains("Bienvenido al portal").contains("UAA");
  }

  @Test
  void invalidTemplateShouldThrowBadRequest() {
    Map<String, Object> configuration = Map.of("message", "hola");

    assertThatThrownBy(
            () -> templateService.processEmailTemplate(EmailTemplate.INVALID, configuration))
        .isInstanceOf(BadRequestException.class);
  }

  @Test
  void nullTemplateConfigurationShouldRenderWithEmptyValues() {
    // lenient by design: a null configuration renders the template with empty sections
    String html = templateService.processEmailTemplate(EmailTemplate.APP_NOTIFICATION, null);

    assertThat(html).isNotBlank();
  }
}
