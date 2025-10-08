CREATE TABLE app_user
(
    user_id    VARCHAR(255) NOT NULL,
    user_email VARCHAR(64)  NOT NULL,
    user_name  VARCHAR(64)  NOT NULL,
    CONSTRAINT app_user_pkey PRIMARY KEY (user_id)
);

CREATE TABLE categories
(
    category_id   VARCHAR(255) NOT NULL,
    category_name VARCHAR(64)  NOT NULL,
    CONSTRAINT categories_pkey PRIMARY KEY (category_id)
);

CREATE TABLE expenses
(
    expense_id              VARCHAR(255)     NOT NULL,
    expense_comment         VARCHAR(128)     NOT NULL,
    expense_cost            DOUBLE PRECISION NOT NULL,
    expense_date            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    category_id_category_id VARCHAR(255)     NOT NULL,
    user_id_user_id         VARCHAR(255)     NOT NULL,
    CONSTRAINT expenses_pkey PRIMARY KEY (expense_id)
);

CREATE TABLE flyway_schema_history
(
    installed_rank INTEGER       NOT NULL,
    version        VARCHAR(50),
    description    VARCHAR(200)  NOT NULL,
    type           VARCHAR(20)   NOT NULL,
    script         VARCHAR(1000) NOT NULL,
    checksum       INTEGER,
    installed_by   VARCHAR(100)  NOT NULL,
    installed_on   TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    execution_time INTEGER       NOT NULL,
    success        BOOLEAN       NOT NULL,
    CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank)
);

ALTER TABLE categories
    ADD CONSTRAINT uk41g4n0emuvcm3qyf1f6cn43c0 UNIQUE (category_name);

ALTER TABLE app_user
    ADD CONSTRAINT ukcpt2jpnop7mcpds1sv2i5629w UNIQUE (user_name);

CREATE INDEX flyway_schema_history_s_idx ON flyway_schema_history (success);

ALTER TABLE expenses
    ADD CONSTRAINT fkdmti2a75tyf7ku9mebe9coxtp FOREIGN KEY (category_id_category_id) REFERENCES categories (category_id) ON DELETE NO ACTION;

ALTER TABLE expenses
    ADD CONSTRAINT fkedpyvlp7jrcr66rnfm4kgvovn FOREIGN KEY (user_id_user_id) REFERENCES app_user (user_id) ON DELETE NO ACTION;