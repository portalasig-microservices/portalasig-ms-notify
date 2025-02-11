package com.portalasig.ms.notify.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.portalasig.ms.commons.persistence.CodeToEnumMapper;
import com.portalasig.ms.commons.persistence.Codeable;

public enum EmailTemplate implements Codeable<String> {

    TEST_TEMPLATE("TEST_TEMPLATE"),
    INVALID("");

    private static final CodeToEnumMapper<String, EmailTemplate> CODE_TO_ENUM_MAPPER =
            new CodeToEnumMapper<>(EmailTemplate.class);

    final String code;

    EmailTemplate(String code) {
        this.code = code;
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
