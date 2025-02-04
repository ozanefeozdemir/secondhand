CREATE TABLE user
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    first_name  VARCHAR(255) NULL,
    middle_name VARCHAR(255) NULL,
    last_name   VARCHAR(255) NULL,
    email       VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);
ALTER TABLE user
    ADD is_active BIT(1) NULL;

ALTER TABLE user
    ADD CONSTRAINT `uc_user_emaıl` UNIQUE (email);


CREATE TABLE user_details
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    phone_number VARCHAR(255)          NULL,
    address      VARCHAR(255)          NULL,
    city         VARCHAR(255)          NULL,
    country      VARCHAR(255)          NULL,
    post_code    VARCHAR(255)          NULL,
    user_id      BIGINT                NOT NULL,
    CONSTRAINT `pk_userdetaıls` PRIMARY KEY (id)
);

ALTER TABLE user_details
    ADD CONSTRAINT FK_USERDETAILS_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);