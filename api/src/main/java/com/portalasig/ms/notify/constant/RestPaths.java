package com.portalasig.ms.notify.constant;

import com.portalasig.ms.commons.constants.RestConstants;

/**
 * Defines REST endpoint paths for notification-related operations.
 */
public class RestPaths {

    /**
     * Contains REST endpoint path constants for email-related operations.
     * <p>
     * This class centralizes the definition of email API paths to ensure consistency
     * across the application.
     * </p>
     */
    public static class Email {
        public static final String EMAIL = "/email";
        public static final String BASE = RestConstants.VERSION_ONE + EMAIL;
    }
}