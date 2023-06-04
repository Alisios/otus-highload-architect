create table if not exists friends
(
    user_id     text                    not null references social_network.users (id) on delete cascade not null,
    friend_id   text                    not null references social_network.users (id) on delete cascade not null,
    create_date timestamp DEFAULT now() not null,
    primary key (user_id, friend_id)
);
