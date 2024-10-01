CREATE TABLE course
(
    course_id      INT          NOT NULL AUTO_INCREMENT COMMENT 'primary key, auto increment',
    code           VARCHAR(128) NOT NULL COMMENT 'Course code. Serves as a identifier',
    name           VARCHAR(128) NOT NULL COMMENT 'Course name',
    credit_units   SMALLINT UNSIGNED NOT NULL COMMENT 'Number of credit units',
    type           VARCHAR(128) NOT NULL COMMENT 'Course type',
    requirements   VARCHAR(255) COMMENT 'Course requirements',
    section        VARCHAR(30)  NOT NULL COMMENT 'Course section',
    career         VARCHAR(128) NOT NULL COMMENT 'Course career',
    classification VARCHAR(128) NOT NULL COMMENT 'Course classification',
    created_date   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'date and time this row was created',
    updated_date   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'date and time this row was last updated',
    PRIMARY KEY (course_id),
    UNIQUE KEY (code),
    KEY            course_idx1 (`section`),
    KEY            course_idx2 (career),
    KEY            course_idx3 (classification)
) COMMENT 'A course that is offered by college';

CREATE TABLE semester
(
    semester_id     INT          NOT NULL AUTO_INCREMENT COMMENT 'primary key, auto increment',
    academic_period VARCHAR(32)  NOT NULL COMMENT 'Academic period',
    description     VARCHAR(128) NOT NULL COMMENT 'Semester description',
    name            VARCHAR(128) NOT NULL COMMENT 'Semester name',
    start_date      DATE         NOT NULL COMMENT 'Semester start date',
    end_date        DATE         NOT NULL COMMENT 'Semester end date',
    is_active       TINYINT(1)      NOT NULL DEFAULT FALSE COMMENT 'Is this semester active?',
    created_date    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'date and time this row was created',
    updated_date    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'date and time this row was last updated',
    PRIMARY KEY (semester_id),
    KEY             semester_idx1 (academic_period)
) COMMENT 'A semester in one academic period';

CREATE TABLE course_semester
(
    course_semester_id INT       NOT NULL AUTO_INCREMENT COMMENT 'primary key, auto increment',
    course_id          INT       NOT NULL COMMENT 'Course ID',
    semester_id        INT       NOT NULL COMMENT 'Semester ID',
    created_date       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'date and time this row was created',
    updated_date       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'date and time this row was last updated',
    PRIMARY KEY (course_semester_id)
) COMMENT 'A course that is offered in a semester';