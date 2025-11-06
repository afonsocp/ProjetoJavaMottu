-- Dev-only seed: roles and admin user (idempotente)
-- Executado apenas quando 'spring.flyway.locations' inclui 'classpath:db/dev'

DELETE FROM usuarios_roles;
DELETE FROM usuarios;
DELETE FROM roles;

MERGE INTO roles (name) KEY(name) VALUES ('ROLE_ADMIN');
MERGE INTO roles (name) KEY(name) VALUES ('ROLE_GERENTE');
MERGE INTO roles (name) KEY(name) VALUES ('ROLE_OPERADOR');

MERGE INTO usuarios (nome, email, senha, ativo) KEY(email)
VALUES ('Administrador NeoMoto', 'admin@neomoto.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', TRUE);

MERGE INTO usuarios (nome, email, senha, ativo) KEY(email)
VALUES ('Gerente Silva', 'gerente@neomoto.com', '$2a$10$CwTycUXWue0Thq9StjUM0uNUHEOcpxMIT.oQN/Yl4MEtzU8NQ/ScG', TRUE);

MERGE INTO usuarios (nome, email, senha, ativo) KEY(email)
VALUES ('Operador Santos', 'operador@neomoto.com', '$2a$10$CJ3VPSo5e5b2QAZ/1IjJf.qQgG7ExOkBJ8gF5yLG.7hq8qPY/8XbG', TRUE);

MERGE INTO usuarios_roles (usuario_id, role_id)
KEY(usuario_id, role_id)
VALUES ((SELECT id FROM usuarios WHERE email = 'admin@neomoto.com'), (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'));

MERGE INTO usuarios_roles (usuario_id, role_id)
KEY(usuario_id, role_id)
VALUES ((SELECT id FROM usuarios WHERE email = 'gerente@neomoto.com'), (SELECT id FROM roles WHERE name = 'ROLE_GERENTE'));

MERGE INTO usuarios_roles (usuario_id, role_id)
KEY(usuario_id, role_id)
VALUES ((SELECT id FROM usuarios WHERE email = 'operador@neomoto.com'), (SELECT id FROM roles WHERE name = 'ROLE_OPERADOR'));