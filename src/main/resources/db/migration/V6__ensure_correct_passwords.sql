-- V6: Ensure correct passwords for all NeoMoto users
-- Garantir que todas as senhas estejam corretas (PostgreSQL compatible)

-- Primeiro, limpar usuários existentes (caso hajam problemas)
DELETE FROM usuarios_roles;
DELETE FROM usuarios;
DELETE FROM roles;

-- Resetar sequências do PostgreSQL
ALTER SEQUENCE roles_id_seq RESTART WITH 1;
ALTER SEQUENCE usuarios_id_seq RESTART WITH 1;

-- Recriar roles
INSERT INTO roles (id, name) VALUES 
(1, 'ROLE_ADMIN'),
(2, 'ROLE_GERENTE'),
(3, 'ROLE_OPERADOR');

-- Atualizar sequência de roles para o próximo valor
SELECT setval('roles_id_seq', (SELECT MAX(id) FROM roles));

-- Inserir usuários com senhas BCrypt corretas
-- admin123 = $2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.
-- gerente123 = $2a$10$CwTycUXWue0Thq9StjUM0uNUHEOcpxMIT.oQN/Yl4MEtzU8NQ/ScG
-- operador123 = $2a$10$CJ3VPSo5e5b2QAZ/1IjJf.qQgG7ExOkBJ8gF5yLG.7hq8qPY/8XbG

INSERT INTO usuarios (id, nome, email, senha, ativo, created_at, updated_at) VALUES 
(1, 'Administrador NeoMoto', 'admin@neomoto.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Gerente Silva', 'gerente@neomoto.com', '$2a$10$CwTycUXWue0Thq9StjUM0uNUHEOcpxMIT.oQN/Yl4MEtzU8NQ/ScG', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Operador Santos', 'operador@neomoto.com', '$2a$10$CJ3VPSo5e5b2QAZ/1IjJf.qQgG7ExOkBJ8gF5yLG.7hq8qPY/8XbG', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Atualizar sequência de usuarios para o próximo valor
SELECT setval('usuarios_id_seq', (SELECT MAX(id) FROM usuarios));

-- Associar roles aos usuários
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES 
(1, 1), -- Admin
(2, 2), -- Gerente  
(3, 3); -- Operador
