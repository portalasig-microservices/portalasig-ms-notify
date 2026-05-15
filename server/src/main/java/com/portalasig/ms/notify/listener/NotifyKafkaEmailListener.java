package com.portalasig.ms.notify.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.portalasig.ms.notify.constant.EmailTemplate;
import com.portalasig.ms.notify.dto.EmailRequest;
import com.portalasig.ms.notify.producer.EmailEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Kafka listener that receives notify events and republishes them as internal email events.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NotifyKafkaEmailListener {

    private final ObjectMapper objectMapper;
    private final EmailEventProducer emailEventProducer;

    /**
     * Handles one raw JSON event from Kafka.
     *
     * @param payload event body with the same contract as EmailRequest
     */
    @KafkaListener(
            topics = "${portalasig.notify.kafka.topic:portalasig.notify.events}",
            groupId = "${portalasig.notify.kafka.group-id:ms-notify}"
    )
    public void onMessage(String payload) {
        try {
            JsonNode root = objectMapper.readTree(payload);
            String emailTo = readText(root, "email_to", "emailTo");
            String subject = readText(root, "subject", "subject");
            String templateCode = readText(root, "template", "template");
            JsonNode templateConfigNode = firstNode(root, "template_configuration", "templateConfiguration");

            EmailTemplate template = EmailTemplate.fromCode(templateCode);
            if (!StringUtils.hasText(emailTo)
                    || !StringUtils.hasText(subject)
                    || template == EmailTemplate.INVALID
                    || templateConfigNode == null
                    || templateConfigNode.isNull()) {
                log.error("Invalid notify Kafka payload: {}", payload);
                return;
            }

            EmailRequest request = EmailRequest.builder()
                    .emailTo(emailTo)
                    .subject(subject)
                    .template(template)
                    .templateConfiguration(objectMapper.convertValue(templateConfigNode, Object.class))
                    .build();

            log.info(
                    "Notify Kafka event received for email_to={}, template={}",
                    request.getEmailTo(),
                    request.getTemplate()
            );
            emailEventProducer.publish(request);
        } catch (Exception exception) {
            log.error("Invalid notify Kafka payload: {}", payload, exception);
        }
    }

    private String readText(JsonNode node, String primary, String secondary) {
        JsonNode primaryNode = node.get(primary);
        if (primaryNode != null && !primaryNode.isNull()) {
            return primaryNode.asText();
        }
        JsonNode secondaryNode = node.get(secondary);
        if (secondaryNode != null && !secondaryNode.isNull()) {
            return secondaryNode.asText();
        }
        return null;
    }

    private JsonNode firstNode(JsonNode node, String primary, String secondary) {
        JsonNode primaryNode = node.get(primary);
        if (primaryNode != null && !primaryNode.isNull()) {
            return primaryNode;
        }
        JsonNode secondaryNode = node.get(secondary);
        if (secondaryNode != null && !secondaryNode.isNull()) {
            return secondaryNode;
        }
        return null;
    }
}
