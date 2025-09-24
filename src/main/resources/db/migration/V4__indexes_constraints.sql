-- V4: Indexes and constraints
-- Criação de índices úteis e constraints adicionais

-- Índices para melhorar performance
CREATE INDEX idx_motos_placa ON motos(placa);
CREATE INDEX idx_motos_status ON motos(status);
CREATE INDEX idx_motoristas_cpf ON motoristas(cpf);
CREATE INDEX idx_motoristas_ativo ON motoristas(ativo);
CREATE INDEX idx_alocacoes_moto_id ON alocacoes(moto_id);
CREATE INDEX idx_alocacoes_motorista_id ON alocacoes(motorista_id);
CREATE INDEX idx_alocacoes_status ON alocacoes(status);
CREATE INDEX idx_manutencoes_moto_id ON manutencoes(moto_id);
CREATE INDEX idx_manutencoes_status ON manutencoes(status);

-- Constraints de validação (H2 compatível)
ALTER TABLE motos ADD CONSTRAINT chk_motos_ano CHECK (ano >= 2000 AND ano <= 2030);
ALTER TABLE motos ADD CONSTRAINT chk_motos_km_atual CHECK (km_atual >= 0);
ALTER TABLE motos ADD CONSTRAINT chk_motos_status CHECK (status IN ('DISPONIVEL', 'ALOCADA', 'MANUTENCAO', 'INATIVA'));

ALTER TABLE alocacoes ADD CONSTRAINT chk_alocacoes_status CHECK (status IN ('ATIVA', 'ENCERRADA'));
ALTER TABLE alocacoes ADD CONSTRAINT chk_alocacoes_data_saida CHECK (data_hora_saida IS NOT NULL);

ALTER TABLE manutencoes ADD CONSTRAINT chk_manutencoes_status CHECK (status IN ('ABERTA', 'FECHADA'));
ALTER TABLE manutencoes ADD CONSTRAINT chk_manutencoes_aberto_em CHECK (aberto_em IS NOT NULL);

-- Nota: Triggers para updated_at são específicos do PostgreSQL
-- H2 não suporta PL/pgSQL, então serão implementados na aplicação
