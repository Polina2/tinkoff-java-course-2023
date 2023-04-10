--liquibase formatted sql

--changeset haku:1
create table tg_chat(
                        chat_id bigint generated always as identity
                            (start with 1 increment by 1) primary key,
                        name varchar(255)
);