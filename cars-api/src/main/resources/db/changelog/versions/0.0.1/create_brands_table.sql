--liquibase formatted sql
--changeset rmartikan:create_brands_table splitStatements:true endDelimiter:;

CREATE TABLE IF NOT EXISTS brands (
    id bigserial PRIMARY KEY,
    name varchar(255) UNIQUE NOT NULL
);

-- rollback drop table brands;