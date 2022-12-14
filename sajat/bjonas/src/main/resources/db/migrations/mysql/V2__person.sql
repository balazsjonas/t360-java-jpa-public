create table employees.Person
(
    id   int auto_increment,
    ssn  varchar(10) null,
    name varchar(32) null,
    constraint Person_pk
        primary key (id)
);