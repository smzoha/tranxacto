CREATE TABLE login (
    id         BIGINT       NOT NULL,
    username   VARCHAR(64)  NOT NULL,
    password   VARCHAR(255) NOT NULL,
    status     VARCHAR(64)  NOT NULL,
    email      VARCHAR(128) NOT NULL,
    first_name VARCHAR(64)  NOT NULL,
    last_name  VARCHAR(64)  NOT NULL,
    CONSTRAINT pk_login PRIMARY KEY (id),
    CONSTRAINT uk_username UNIQUE (username)
);

CREATE SEQUENCE seq_login START WITH 1 INCREMENT BY 1;

CREATE TABLE roles (
    username VARCHAR(64)  NOT NULL,
    role     VARCHAR(128) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (username, role),
    CONSTRAINT fk_role_username FOREIGN KEY (username) REFERENCES login (username)
);
