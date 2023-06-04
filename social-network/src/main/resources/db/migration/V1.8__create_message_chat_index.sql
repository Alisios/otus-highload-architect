create index IF NOT EXISTS ind_chat_id_prefix on social_network.messages(chat_id text_pattern_ops);

create index IF NOT EXISTS ind_userid_prefix on social_network.chats(from_user_id text_pattern_ops);

