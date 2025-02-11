package com.portalasig.ms.notify.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.portalasig.ms.notify.constant.EmailTemplate;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    @ApiModelProperty(value = "Email to address")
    @NotNull
    private String emailTo;

    @ApiModelProperty(value = "Email subject")
    @NotNull
    private String subject;

    @ApiModelProperty(value = "Email template")
    @NotNull
    private EmailTemplate template;

    @ApiModelProperty(value = "Email configuration")
    @NotNull
    private JsonNode templateConfiguration;
}
