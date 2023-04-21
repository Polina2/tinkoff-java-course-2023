--liquibase formatted sql

--changeset haku:1
create table subscription(
                             chat_id bigint,
                             link_id bigint,
                             foreign key (chat_id) references tg_chat (id) on delete cascade,
                             foreign key (link_id) references link (id) on delete cascade,
                             primary key (chat_id, link_id)
);
