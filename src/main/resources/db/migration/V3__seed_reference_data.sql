-- V3: Seed reference data
-- Inserção de dados de exemplo para pátios, motoristas e motos

-- Inserir pátios
INSERT INTO patios (nome, endereco, capacidade_opcional, observacoes) VALUES 
('Centro', 'Rua das Flores, 123 - Centro', 50, 'Pátio principal do centro da cidade'),
('Zona Leste', 'Av. Industrial, 456 - Zona Leste', 30, 'Pátio da zona industrial');

-- Inserir motoristas
INSERT INTO motoristas (nome, cpf, cnh, validade_cnh, telefone, email, ativo) VALUES 
('João Silva', '12345678901', '12345678901', '2026-12-31', '(11) 99999-1111', 'joao.silva@email.com', TRUE),
('Maria Santos', '98765432109', '98765432109', '2026-06-30', '(11) 99999-2222', 'maria.santos@email.com', TRUE),
('Pedro Oliveira', '11122233344', '11122233344', '2026-09-15', '(11) 99999-3333', 'pedro.oliveira@email.com', TRUE);

-- Inserir motos
INSERT INTO motos (placa, modelo, ano, km_atual, status, chassi, renavam, observacoes) VALUES 
('ABC1234', 'Honda CG 160', 2023, 15000, 'DISPONIVEL', '9BWZZZZZZZZZZZZZZZ', '12345678901', 'Moto em excelente estado'),
('DEF5678', 'Yamaha Fazer 250', 2022, 25000, 'DISPONIVEL', '9BWYYYYYYYYYYYYYYY', '23456789012', 'Moto com poucos quilômetros'),
('GHI9012', 'Honda CB 300', 2023, 8000, 'ALOCADA', '9BWXXXXXXXXXXXXXXX', '34567890123', 'Moto nova, alocada para entrega'),
('JKL3456', 'Yamaha XTZ 250', 2021, 35000, 'MANUTENCAO', '9BWWWWWWWWWWWWWWWWW', '45678901234', 'Em manutenção preventiva');

-- Inserir alocação ativa (para demonstrar devolução)
INSERT INTO alocacoes (moto_id, motorista_id, patio_origem_id, data_hora_saida, checklist_saida, status) VALUES 
(3, 1, 1, '2024-01-15 08:00:00', 'Pneus ok, freios ok, combustível cheio, documentos em dia', 'ATIVA');

-- Inserir manutenção aberta (para testar bloqueio)
INSERT INTO manutencoes (moto_id, aberto_em, aberto_por, motivo, status) VALUES 
(4, '2024-01-10 14:30:00', 'admin@neomoto.com', 'Manutenção preventiva - troca de óleo e filtros', 'ABERTA');
