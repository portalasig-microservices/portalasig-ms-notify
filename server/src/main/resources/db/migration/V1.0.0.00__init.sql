CREATE TABLE test
(
    test_id      BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'test id',
    a_field     VARCHAR(250) NOT NULL COMMENT 'A_FIELD',
    created_date datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'date and time this row was created',
    updated_date timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'date and time this row was last updated',
    PRIMARY KEY (test_id),
    KEY test_idx1 (a_field)
)
    COMMENT 'test table';