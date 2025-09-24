-- V2: Seed roles and admin user
-- Inserção dos roles e usuário administrador inicial

-- Inserir roles
INSERT INTO roles (name) VALUES 
('ROLE_ADMIN'),
('ROLE_GERENTE'),
('ROLE_OPERADOR');

-- Inserir usuários
-- Senhas: admin123, gerente123, operador123 (BCrypt)
INSERT INTO usuarios (nome, email, senha, ativo) VALUES 
('Administrador', 'admin@neomoto.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', TRUE),
('Gerente Silva', 'gerente@neomoto.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', TRUE),
('Operador Santos', 'operador@neomoto.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', TRUE);

-- Associar roles aos usuários
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES 
(1, 1), -- Admin tem ROLE_ADMIN
(2, 2), -- Gerente tem ROLE_GERENTE  
(3, 3); -- Operador tem ROLE_OPERADOR
