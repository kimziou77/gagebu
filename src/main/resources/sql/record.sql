create table record
(
    id         bigint       not null primary key,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    category   varchar(255) null,
    deleted    bit          not null,
    memo       varchar(255) null,
    money      int          null,
    name       varchar(255) null,
    user_id    bigint       null,
    constraint FK44ctj7m4iik9qhcbaqt1aynka
        foreign key (user_id) references users (id)
);

