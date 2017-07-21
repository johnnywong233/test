INSERT INTO sys_role (id, name) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');

INSERT INTO sys_user (id, password, username) VALUES (1, 'root', 'root'), (2, 'johnny', 'johnny');

INSERT INTO sys_user_roles (sys_user_id, roles_id) VALUES (1, 1), (2, 2);