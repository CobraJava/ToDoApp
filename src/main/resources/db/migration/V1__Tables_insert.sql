DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tasks;

create table users
(
    id bigint generated always as identity primary key,
    username varchar(30) not null,
    password varchar(200),
    email varchar(200),
    role varchar(30)
);

CREATE TABLE tasks
(
    id bigint generated always as identity primary key,
    name varchar(2000) not null,
    status varchar(15) not null,
    created_at timestamp not null,
    user_id bigint REFERENCES users(id)
);




