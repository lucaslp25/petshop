-- Criação do banco de dados com o nome que desejar
CREATE DATABASE super_petshop;  -- <--aqui pode alterar o nome do database antes de correr a query!

-- tabela do endereço
CREATE TABLE endereco (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          rua VARCHAR(255) NOT NULL,
                          numero VARCHAR(10),
                          bairro VARCHAR(100),
                          cidade VARCHAR(100) NOT NULL,
                          estado VARCHAR(50) NOT NULL,
                          cep VARCHAR(10),
                          complemento VARCHAR(255)
);

-- tabela de cliente
CREATE TABLE cliente (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         cpf VARCHAR(15) UNIQUE NOT NULL, -- caracteristica de CPF unico por cliente em UNIQUE NOT NULL
                         email VARCHAR(100),
                         telefone VARCHAR(20),
                         endereco_id INT, -- chave estrangeira para a tabela endereco
                         FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

-- tabela metodo_pagamento
CREATE TABLE metodo_pagamento (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  tipo VARCHAR(50) UNIQUE NOT NULL
);

-- tabela funcionario
CREATE TABLE funcionario (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             nome VARCHAR(100) NOT NULL,
                             cargo VARCHAR(30),
                             endereco_id INT,
                             FOREIGN KEY (endereco_id) REFERENCES endereco(id),
                             salario DECIMAL(10,2) -- tipo de dado indicado para valores em dinheiro
);

-- tabela produto
CREATE TABLE produto (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         descricao VARCHAR(300),
                         preco_custo DECIMAL(10, 2) NOT NULL,
                         preco_venda DECIMAL(10,2) NOT NULL,
                         quantidade_estoque INT NOT NULL,
                         fornecedor VARCHAR (150),
                         categoria VARCHAR (50) NOT NULL
);

-- tabela servico
CREATE TABLE servico (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         descricao VARCHAR(300),
                         preco DECIMAL(10, 2) NOT NULL,
                         duracao INT NOT NULL,
                         tipo_servico VARCHAR(50) NOT NULL
);

-- tabela pet
CREATE TABLE pet (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     nome VARCHAR(255) NOT NULL,
                     especie VARCHAR(100),
                     raca VARCHAR(100),
                     porte VARCHAR(30),
                     cliente_id INT NOT NULL, -- chave estrangeira para a tabela cliente
                     FOREIGN KEY (cliente_id) REFERENCES cliente(id),
                     idade INT NOT NULL -- idade em anos
);

-- tabela venda
CREATE TABLE venda (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       data_venda DATE NOT NULL,
                       valor_total DECIMAL(10, 2) NOT NULL,
                       cliente_id INT NOT NULL,
                       metodo_pagamento_id INT NOT NULL,
                       FOREIGN KEY (cliente_id) REFERENCES cliente(id),
                       FOREIGN KEY (metodo_pagamento_id) REFERENCES metodo_pagamento(id)
);

-- tabela item_venda | representa cada produto comprado dentro de uma venda específica
CREATE TABLE item_venda (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            venda_id INT NOT NULL,
                            preco_unitario DECIMAL(10, 2) NOT NULL, -- preço do produto no momento da venda
                            quantidade INT NOT NULL,
                            produto_id INT,
                            servico_id INT,
                            FOREIGN KEY (venda_id) REFERENCES venda(id),
                            FOREIGN KEY (produto_id) REFERENCES produto(id),
                            FOREIGN KEY (servico_id) REFERENCES servico(id)
);

-- tabela agendamento
CREATE TABLE agendamento (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             data_hora DATETIME NOT NULL,
                             pet_id INT NOT NULL,
                             funcionario_id INT NOT NULL,
                             servico_id INT NOT NULL,
                             valor DECIMAL(10, 2) NOT NULL,
                             FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
                             FOREIGN KEY (pet_id) REFERENCES pet(id),
                             FOREIGN KEY (servico_id) REFERENCES servico(id)
);

-- tabela de servico_banho
CREATE TABLE servico_banho(

                              servico_id INT PRIMARY KEY,
                              com_hidratacao BOOLEAN,
                              FOREIGN KEY (servico_id) REFERENCES servico(id)  -- usa o id da super classe serviços como chave primaria e estrangeria aqui
);

-- tabela de servico_tosa
CREATE TABLE servico_tosa(

                             servico_id INT PRIMARY KEY,
                             inclui_escovacao BOOLEAN,
                             inclui_banho_previo BOOLEAN,
                             FOREIGN KEY (servico_id) REFERENCES servico(id)
);

-- tabela de servico_vacinacao
CREATE TABLE servico_vacinacao(

                                  servico_id INT PRIMARY KEY,
                                  tipo_de_vacinacao VARCHAR(60),
                                  FOREIGN KEY (servico_id) REFERENCES servico(id)
);

-- tabela de servico_consulta_veterinaria
CREATE TABLE servico_consulta_veterinaria(

                                             servico_id INT PRIMARY KEY,
                                             tipo_de_consulta VARCHAR(60),
                                             FOREIGN KEY (servico_id) REFERENCES servico(id)
);


-- Scripts para inserir na tabela!

INSERT INTO metodo_pagamento (tipo) VALUES ('PIX'),
                                           ( 'DINHEIRO'),
                                           ( 'CARTAO_DE_CREDITO'),
                                           ('CARTAO_DE_DEBITO'),
                                           ( 'BOLETO');

-- popular a tabela de pagamentos com os popularmente aceitos!

