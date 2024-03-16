DROP TABLE IF EXISTS purchase;
CREATE TABLE purchase (
    id bigserial primary key,
    description varchar(255) null,
    amount numeric(14,2) null,
    transaction_date date null
);