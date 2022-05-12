insert into usuarios(username,password,enabled, nombre, apellido, email) values('andres', '$2a$10$W0RMhI4jgdGEYHAKmsU0uusyOJwZD8GdEdNBfYpbWxLsnaXhmHVea', true, 'Andres', 'Guzman','profe@gmail.com');
insert into usuarios(username,password,enabled, nombre, apellido, email) values('admin', '$2a$10$ej9N0SqwGZ/3yR8KBiCN9uxeia/y/oj5wKlx6U9irekUx2v8WpjbK', true, 'Admin', 'root', 'admin@gmail.com');
insert into usuarios(username,password,enabled, nombre, apellido, email) values('jhon', '$2a$10$sKsHFwLiMnYDbft/WtupxOvSzsvjK7TSqtD2Njtya8zxeNl04fHFO', true, 'Jhon', 'Doe', 'DoeJhon.Doe@gmail.com');


insert into roles(nombre) values('ROLE_USER');
insert into roles(nombre) values('ROLE_ADMIN');

insert into usuarios_roles(usuario_id, role_id) values(1,1);
insert into usuarios_roles(usuario_id, role_id) values(2,2);
insert into usuarios_roles(usuario_id, role_id) values(2,1);
