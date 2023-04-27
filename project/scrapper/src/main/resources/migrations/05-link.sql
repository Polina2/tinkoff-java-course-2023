--liquibase formatted sql

--changeset haku:3
alter table link
add column update_info varchar