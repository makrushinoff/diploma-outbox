create table users
(
    id           bigserial not null,
    email        varchar(255)  not null,
    father_name  varchar(255)  not null,
    first_name   varchar(255)  not null,
    last_name    varchar(255)  not null,
    password     varchar(255)  not null,
    phone_number varchar(255)  not null,
    role         varchar(255)  not null,
    primary key (id)
);

insert into users (email, father_name, first_name, last_name, password, phone_number, role)
    values ('admin@admin.com', 'admin', 'admin', 'admin', '$2a$10$Jm527FQUxAenSrs/iKSflOHj6nOVGeOnsKjxOD79Uuco81HCV7kkC', '+380999999999', 'ADMIN')