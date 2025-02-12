package com.portalasig.ms.notify.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.portalasig.ms.commons.persistence.CodeToEnumMapper;
import com.portalasig.ms.commons.persistence.Codeable;

public enum EmailTemplate implements Codeable<String> {

    APP_NOTIFICATION("APP_NOTIFICATION", "app-notification"),
    SIMPLE_MESSAGE("SIMPLE_MESSAGE", "simple-message"),
    INVALID("", null);

    private static final CodeToEnumMapper<String, EmailTemplate> CODE_TO_ENUM_MAPPER =
            new CodeToEnumMapper<>(EmailTemplate.class);

    final String code;

    final String templateName;

    public final boolean isValid() {
        return this != INVALID;
    }

    public final String getTemplateName() {
        return templateName;
    }

    EmailTemplate(String code, String templateName) {
        this.code = code;
        this.templateName = templateName;
    }

    @JsonCreator
    public static EmailTemplate fromCode(String code) {
        return CODE_TO_ENUM_MAPPER.fromCode(code).isPresent() ? CODE_TO_ENUM_MAPPER.fromCode(code).get() : INVALID;
    }

    @JsonValue
    @Override
    public String getCode() {
        return code;
    }
}
