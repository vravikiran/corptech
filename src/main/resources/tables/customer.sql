create table customer (
customer_id bigint(20) not null,
name varchar(255) not null,
email varchar(255) not null,
phone_no bigint(20) not null,
creation_time timestamp not null default current_timestamp,
primary key(customer_id)
);