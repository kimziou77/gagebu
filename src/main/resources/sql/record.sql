create table record
(
    id         bigint      not null primary key,
    created_at datetime(6) null,
    updated_at datetime(6) null,
    deleted    bit         not null,
    deleted_at datetime(6) null,
    memo       varchar(60) not null,
    money      int         not null,
    name       varchar(20) not null,
    user_id    bigint      not null,
    constraint FK44ctj7m4iik9qhcbaqt1aynka foreign key (user_id) references users (id)
);

