create table users
(
    id         bigint       not null primary key,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    email      varchar(255) null,
    password   varchar(200) null,
    user_role  varchar(255) null,
    verified   bit          not null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)
);
