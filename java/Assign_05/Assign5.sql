/*
 creates the tables I need in my database
*/

drop table if exists customer;

create table customer(
 ssn char(11) not null PRIMARY KEY,
 name varchar(20) not null,
 address varchar(40) not null,
 zipCode char(5) not null
);

insert into customer (ssn, name, address, zipCode) values 
("938-20-4049", "Phil Osophy", "202 sid drive", "93827"),
("028-23-2119", "Cade Ance", "709 v ave", "10293"),
("178-10-0209", "Fin Icky", "109 k street", "29187"),
("138-20-3019", "Terri Ble", "201 regal drive", "57284"),
("238-99-9023", "Catas Trophy", "002 phillip ave", "89271"),
("334-09-0012", "Rhet Oric", "4001 newark ave", "22934"),
("437-11-1010", "Soph Istry", "9073 quantico st", "44922");
