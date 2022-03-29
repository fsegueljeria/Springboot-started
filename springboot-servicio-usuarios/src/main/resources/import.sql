insert into usuarios(username,password,enabled, nombre, apellido, email) values('andres', '12345', 1, 'Andres', 'Guzman','profe@gmail.com');
insert into usuarios(username,password,enabled, nombre, apellido, email) values('jhon', '12345', 1, 'Jhon', 'Doe', 'DoeJhon.Doe@gmail.com');


insert into roles(nombre) values('ROLE_USER');
insert into roles(nombre) values('ROLE_ADMIN');

insert into usuarios_roles(usuario_id, role_id) values(1,1);
insert into usuarios_roles(usuario_id, role_id) values(2,2);
insert into usuarios_roles(usuario_id, role_id) values(2,1);
