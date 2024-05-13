USE master;
GO

CREATE LOGIN loja WITH PASSWORD = 'loja', CHECK_POLICY = OFF;
GO

CREATE USER loja FOR LOGIN loja;
GO

CREATE DATABASE Loja;
GO

USE Loja;
GO

CREATE TABLE [Pessoa] (
	idPessoa int NOT NULL UNIQUE,
	nome varchar(50) NOT NULL,
	logradouro varchar(50) NOT NULL,
	cidade varchar(50) NOT NULL,
	estado char(2) NOT NULL,
	telefone varchar(11) NOT NULL,
	email varchar(50) NOT NULL,
  CONSTRAINT [PK_PESSOA] PRIMARY KEY CLUSTERED
  (
  [idPessoa] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [PessoaFisica] (
	idPessoaFisica int NOT NULL UNIQUE,
	cpf varchar(11) NOT NULL UNIQUE,
  CONSTRAINT [PK_PESSOAFISICA] PRIMARY KEY CLUSTERED
  (
  [idPessoaFisica] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [PessoaJuridica] (
	idPessoaJuridica int NOT NULL UNIQUE,
	cnpj varchar(14) NOT NULL UNIQUE,
  CONSTRAINT [PK_PESSOAJURIDICA] PRIMARY KEY CLUSTERED
  (
  [idPessoaJuridica] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
ALTER TABLE [PessoaFisica] WITH CHECK ADD CONSTRAINT [PessoaFisica_fk0] FOREIGN KEY ([idPessoaFisica]) REFERENCES [Pessoa]([idPessoa])
ON UPDATE CASCADE
GO
ALTER TABLE [PessoaFisica] CHECK CONSTRAINT [PessoaFisica_fk0]
GO
ALTER TABLE [PessoaJuridica] WITH CHECK ADD CONSTRAINT [PessoaJuridica_fk0] FOREIGN KEY ([idPessoaJuridica]) REFERENCES [Pessoa]([idPessoa])
ON UPDATE CASCADE
GO
ALTER TABLE [PessoaJuridica] CHECK CONSTRAINT [PessoaJuridica_fk0]
GO