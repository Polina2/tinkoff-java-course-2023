--liquibase formatted sql

--changeset haku:1
create table tg_chat(
                        id bigint generated always as identity
                            (start with 1 increment by 1) primary key,
                        tg_chat_id bigint not null unique
);