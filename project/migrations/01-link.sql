--liquibase formatted sql

--changeset haku:1
create table link(
                     link_id bigint generated always as identity
                         (start with 1 increment by 1) primary key,
                     url text not null unique,
                     last_update timestamptz
);