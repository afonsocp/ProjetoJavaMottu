-- V1: Create tables
-- Criação das tabelas principais do sistema (PostgreSQL compatible)

-- Tabela de roles
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Tabela de usuários
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de relacionamento usuários e roles
CREATE TABLE usuarios_roles (
    usuario_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, role_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Tabela de motos
CREATE TABLE motos (
    id BIGSERIAL PRIMARY KEY,
    placa VARCHAR(7) NOT NULL UNIQUE,
    modelo VARCHAR(100) NOT NULL,
    ano INTEGER NOT NULL,
    km_atual BIGINT NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'DISPONIVEL',
    chassi VARCHAR(50),
    renavam VARCHAR(20),
    observacoes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de motoristas
CREATE TABLE motoristas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    cnh VARCHAR(20) NOT NULL,
    validade_cnh DATE NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de pátios
CREATE TABLE patios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(200),
    capacidade_opcional INTEGER,
    observacoes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de alocações
CREATE TABLE alocacoes (
    id BIGSERIAL PRIMARY KEY,
    moto_id BIGINT NOT NULL,
    motorista_id BIGINT NOT NULL,
    patio_origem_id BIGINT NOT NULL,
    data_hora_saida TIMESTAMP NOT NULL,
    checklist_saida TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'ATIVA',
    data_hora_devolucao TIMESTAMP,
    checklist_devolucao TEXT,
    patio_devolucao_id BIGINT,
    km_devolucao BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (moto_id) REFERENCES motos(id) ON DELETE RESTRICT,
    FOREIGN KEY (motorista_id) REFERENCES motoristas(id) ON DELETE RESTRICT,
    FOREIGN KEY (patio_origem_id) REFERENCES patios(id) ON DELETE RESTRICT,
    FOREIGN KEY (patio_devolucao_id) REFERENCES patios(id) ON DELETE RESTRICT
);

-- Tabela de manutenções
CREATE TABLE manutencoes (
    id BIGSERIAL PRIMARY KEY,
    moto_id BIGINT NOT NULL,
    aberto_em TIMESTAMP NOT NULL,
    aberto_por VARCHAR(100) NOT NULL,
    motivo VARCHAR(200) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ABERTA',
    fechado_em TIMESTAMP,
    fechado_por VARCHAR(100),
    observacoes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (moto_id) REFERENCES motos(id) ON DELETE RESTRICT
);
