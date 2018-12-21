insert into usr (id, username, password, active)
values (77, 'admin', 'Neskazu0', true);

insert into user_role (user_id, roles)
values (77, 'USER'), (77, 'ADMIN');