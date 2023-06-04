create table if not exists chats
(
    chat_id      text                    not null,
    from_user_id text                    not null,
    to_user_id   text                    not null,
    create_date  timestamp DEFAULT now() not null,
    primary key (chat_id)
);

create table if not exists messages
(
    message_id   text                    not null,
    chat_id      text                    not null references chat.chats (chat_id) on delete cascade not null,
    from_user_id text                    not null,
    to_user_id   text                    not null,
    message_text text                    not null,
    create_date  timestamp DEFAULT now() not null,
    primary key (chat_id, message_id)
);
