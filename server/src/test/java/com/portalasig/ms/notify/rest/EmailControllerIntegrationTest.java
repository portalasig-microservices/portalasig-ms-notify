package com.portalasig.ms.notify.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.portalasig.ms.commons.test.AbstractMysqlIntegrationTest;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
class EmailControllerIntegrationTest extends AbstractMysqlIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private JavaMailSender mailSender;

  @BeforeEach
  void setUpMimeMessage() {
    when(mailSender.createMimeMessage()).thenReturn(new MimeMessage((jakarta.mail.Session) null));
  }

  @Test
  void sendEmailEndpointShouldAcceptRequestAndDispatchEmail() throws Exception {
    String body =
        "{\"email_to\": \"profesor@portalasig.ucv.ve\", \"subject\": \"Aviso\","
            + " \"template\": \"SIMPLE_MESSAGE\","
            + " \"template_configuration\": {\"message\": \"Clase cancelada\", \"by\": \"Site\"}}";

    mockMvc
        .perform(
            post("/v1/email").with(jwt()).contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(status().isOk());

    verify(mailSender, timeout(5000)).send(any(MimeMessage.class));
  }

  @Test
  void sendEmailWithoutAuthenticationShouldBeRejected() throws Exception {
    mockMvc
        .perform(
            post("/v1/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"email_to\": \"x@x.com\", \"subject\": \"s\", \"template\": \"SIMPLE_MESSAGE\","
                        + " \"template_configuration\": {\"message\": \"m\", \"by\": \"b\"}}"))
        .andExpect(status().is4xxClientError());
  }
}
