package com.portalasig.ms.notify.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.portalasig.ms.commons.persistence.CodeToEnumMapper;
import com.portalasig.ms.commons.persistence.Codeable;

/**
 * Enum representing supported email templates in the system.
 * Each template is associated with a code and a file name reference.
 */
public enum EmailTemplate implements Codeable<String> {

    APP_NOTIFICATION("APP_NOTIFICATION", "app-notification"),
    SIMPLE_MESSAGE("SIMPLE_MESSAGE", "simple-message"),
    INVALID("", null);

    private static final CodeToEnumMapper<String, EmailTemplate> CODE_TO_ENUM_MAPPER =
            new CodeToEnumMapper<>(EmailTemplate.class);

    final String code;
    final String templateName;

    /**
     * Indicates whether this enum value is a valid template.
     *
     * @return {@code true} if the template is not INVALID; {@code false} otherwise
     */
    public final boolean isValid() {
        return this != INVALID;
    }

    /**
     * Returns the template file name associated with the enum value.
     *
     * @return the template file name
     */
    public final String getTemplateName() {
        return templateName;
    }

    EmailTemplate(String code, String templateName) {
        this.code = code;
        this.templateName = templateName;
    }

    /**
     * Creates an {@link EmailTemplate} from its string code.
     *
     * @param code the code to match
     * @return the corresponding {@link EmailTemplate}, or {@link #INVALID} if no match is found
     */
    @JsonCreator
    public static EmailTemplate fromCode(String code) {
        return CODE_TO_ENUM_MAPPER.fromCode(code).isPresent()
                ? CODE_TO_ENUM_MAPPER.fromCode(code).get()
                : INVALID;
    }

    /**
     * Returns the code associated with this enum value.
     *
     * @return the code string
     */
    @JsonValue
    @Override
    public String getCode() {
        return code;
    }
}