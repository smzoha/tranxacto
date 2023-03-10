CREATE TABLE supporting_document (
    id          BIGINT       NOT NULL,
    name        VARCHAR(128) NOT NULL,
    size        BIGINT       NOT NULL,
    upload_date TIMESTAMP(6) NOT NULL,
    data        BYTEA        NOT NULL,
    CONSTRAINT pk_supporting_document PRIMARY KEY (id)
);

CREATE SEQUENCE seq_supporting_document START WITH 1 INCREMENT BY 1;
