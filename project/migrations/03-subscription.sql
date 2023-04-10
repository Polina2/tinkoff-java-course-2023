--liquibase formatted sql

--changeset haku:1
create table subscription(
                             chat_id bigint not null,
                             link_id bigint not null,
                             foreign key (chat_id) references tg_chat (chat_id) on delete cascade,
                             foreign key (link_id) references link (link_id) on delete cascade,
                             primary key (chat_id, link_id)
);
