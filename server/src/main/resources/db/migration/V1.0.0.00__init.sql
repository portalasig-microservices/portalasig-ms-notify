CREATE TABLE notification
(
    notification_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'notification id',
    title           VARCHAR(255) NOT NULL COMMENT 'notification title',
    message         TEXT         NOT NULL COMMENT 'notification message',
    type            VARCHAR(30)  NOT NULL COMMENT 'notification type',
    status          VARCHAR(30)  NOT NULL COMMENT 'notification status',
    created_date    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'date and time this row was created',
    updated_date    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'date and time this row was last updated',
    PRIMARY KEY (notification_id)
) COMMENT 'Holds all notifications for the application';