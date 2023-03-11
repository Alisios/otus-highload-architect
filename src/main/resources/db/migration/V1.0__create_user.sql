create table users
(
    id          text                    not null,
    "login"     text                    not null,
    "password"  text                    not null,
    "name"      text                    not null,
    surname     text                    not null,
    age         int8                    not null,
    gender      text                    ,
    city        text                    not null,
    interests   text[],
    create_date timestamp DEFAULT now() not null,
    modify_date timestamp DEFAULT now() not null,
    primary key (id)
);
