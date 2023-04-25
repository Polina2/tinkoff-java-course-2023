--liquibase formatted sql

--changeset haku:2
alter table link
add column last_check timestamp default current_timestamp