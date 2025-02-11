package com.portalasig.ms.notify.dto;

import com.portalasig.ms.notify.constant.EmailTemplate;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @ApiModelProperty(value = "Email to address")
    private String emailTo;

    @ApiModelProperty(value = "Email subject")
    private String subject;

    @ApiModelProperty(value = "Email template")
    private EmailTemplate template;

    @ApiModelProperty(value = "Timestamp when the email was sent")
    private Instant sentAt;
}
