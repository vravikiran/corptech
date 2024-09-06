create table user_info(
id int(11) not null auto_increment,
name varchar(255) not null,
email varchar(255),
roles varchar(255),
password varchar(255) not null,
primary key(id)
);