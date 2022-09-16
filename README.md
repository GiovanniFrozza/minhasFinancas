# minhasFinancas
Estudo de Spring Boot e React JS



# Criação de tabela PostgreSQL

CREATE DATABASE minhasfinancas

create schema financas

CREATE TABLE financas.usuario <br />
( <br />
    id bigserial NOT NULL PRIMARY KEY, <br />
    nome character varying(150), <br />
    email character varying(100), <br />
    senha character varying(20), <br />
    data_cadastro date DEFAULT now(), <br />
)

CREATE TABLE financas.lancamento  <br />
( <br />
    id bigserial NOT NULL PRIMARY KEY, <br /> 
    descricao character varying(100) NOT NULL, <br />
    mes integer NOT NULL, <br />
    ano integer NOT NULL, <br />
    valor numeric(16,2) NOT NULL, <br />
    tipo character varying(20) check(tipo in ('RECEITA', 'DESPESA')) NOT NULL, <br />
    status character varying(20) check(status in ('PENDENTE','CANCELADO','EFETIVADO')) NOT NULL, <br />
    id_usuario bigint REFERENCES financas.usuario(id) NOT NULL, <br />
    data_cadastro date DEFAULT now() <br />
) <br />
