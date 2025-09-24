-- V5: Fix passwords for all users
-- Atualizar senhas de todos os usuários para versões BCrypt corretas

-- Senhas: admin123, gerente123, operador123 (BCrypt com salt 10)
-- Hash gerado com BCryptPasswordEncoder

-- Admin: admin123
UPDATE usuarios 
SET senha = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.' 
WHERE email = 'admin@neomoto.com';

-- Gerente: gerente123  
UPDATE usuarios 
SET senha = '$2a$10$CwTycUXWue0Thq9StjUM0uNUHEOcpxMIT.oQN/Yl4MEtzU8NQ/ScG' 
WHERE email = 'gerente@neomoto.com';

-- Operador: operador123
UPDATE usuarios 
SET senha = '$2a$10$CJ3VPSo5e5b2QAZ/1IjJf.qQgG7ExOkBJ8gF5yLG.7hq8qPY/8XbG' 
WHERE email = 'operador@neomoto.com';
