create table interests
(
    id       text not null,
    interest text
);

create table users
(
    id          text                    not null,
    "login"     text                    not null,
    "password"  text                    not null,
    "name"      text                    not null,
    surname     text                    not null,
    age         int8                    not null,
    gender      text                    not null,
    city        text                    not null,
    create_date timestamp DEFAULT now() not null,
    modify_date timestamp DEFAULT now() not null,
    primary key (id)
);

alter table if exists interests
    add constraint FKgiuld0vpxb9r9ntfyimfqbcjx foreign key (id) references users;
