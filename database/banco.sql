CREATE DATABASE IF NOT EXISTS stock_manager;

USE stock_manager;

CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    categoria VARCHAR(50) NOT NULL,
    quantidade INT NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    fornecedor VARCHAR(100),
    cep VARCHAR(9),
    logradouro VARCHAR(150),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    descricao TEXT
);