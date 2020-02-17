delete from user_roles where user_id > 0;
delete from roles where id > 0;
delete from user where id > 0;

insert into user(id, email, password, username) values(1, 'admin@parrotsays.com', '$2a$10$PPwtFyviu58YK6HhVISi1uzoidjb0voeYbt93hAfBNQLKMMoBl7hS', 'admin');

insert into roles(id, nome) values(1, 'ROLE_ADMIN');
insert into roles(id, nome) values(2, 'ROLE_SECGUARD');
insert into roles(id, nome) values(3, 'ROLE_USER');

insert into user_roles(user_id, role_id) values(1,1);
