create table if not exists posts
(
    post_id     text                    not null,
    user_id     text                    not null references social_network.users (id) on delete cascade not null,
    post_text   text                    not null,
    create_date timestamp DEFAULT now() not null,
    modify_date timestamp DEFAULT now() not null,
    primary key (post_id)
);
