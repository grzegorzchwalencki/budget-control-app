CREATE TABLE users
(
    user_id    UUID        NOT NULL,
    user_name  VARCHAR(64) NOT NULL,
    user_email VARCHAR(64) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id),
    CONSTRAINT uk_users_user_name UNIQUE (user_name)
);

