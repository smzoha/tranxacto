CREATE TABLE account_type (
    id BIGINT NOT NULL,
    code VARCHAR(64) NOT NULL,
    description VARCHAR(512) NOT NULL,
    CONSTRAINT pk_account_type PRIMARY KEY (id)
);

CREATE SEQUENCE seq_account_type START WITH 1 INCREMENT BY 1;

CREATE TABLE account (
    id BIGINT NOT NULL,
    name VARCHAR(128) NOT NULL,
    type BIGINT NOT NULL,
    account_number VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    status VARCHAR(16) NOT NULL,
    opening_balance NUMERIC(10, 2) DEFAULT 0,
    organization VARCHAR(255),
    created_date TIMESTAMP(6) NOT NULL,
    updated_date TIMESTAMP(6) NOT NULL,
    document_id BIGINT,
    CONSTRAINT pk_account PRIMARY KEY (id),
    CONSTRAINT fk_account_type FOREIGN KEY (type) REFERENCES account_type(id)
);

CREATE SEQUENCE seq_account START WITH 1 INCREMENT BY 1;

INSERT INTO account_type VALUES(nextval('seq_account_type'), 'CASH', 'Cash');
INSERT INTO account_type VALUES(nextval('seq_account_type'), 'SAVINGS', 'Savings Account');
INSERT INTO account_type VALUES(nextval('seq_account_type'), 'CURRENT', 'Current Account');
INSERT INTO account_type VALUES(nextval('seq_account_type'), 'M_WALLET', 'Mobile Wallet');
INSERT INTO account_type VALUES(nextval('seq_account_type'), 'O_WALLET', 'Online Wallet');
INSERT INTO account_type VALUES(nextval('seq_account_type'), 'DEBIT_CARD', 'Debit Card');
INSERT INTO account_type VALUES(nextval('seq_account_type'), 'CREDIT_CARD', 'Credit Card');
INSERT INTO account_type VALUES(nextval('seq_account_type'), 'PREPAID_CARD', 'Pre-Paid Card');
INSERT INTO account_type VALUES(nextval('seq_account_type'), 'GIFT_CARD', 'Gift Card');
