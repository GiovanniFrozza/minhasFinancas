# minhasFinancas
Estudo de Spring Boot e React JS



# Criação de tabela PostgreSQL

CREATE TABLE financas.lancamento  <br />
( <br />
    <div style="margin-right: 30px;">
     id bigint NOT NULL, <br />
        descricao character varying(100) NOT NULL, <br />
        mes integer NOT NULL, <br />
        ano integer NOT NULL, <br />
        valor numeric(16,2) NOT NULL, <br />
        tipo character varying(20) NOT NULL, <br />
        status character varying(20) NOT NULL, <br />
        id_usuario bigint NOT NULL, <br />
        data_cadastro date DEFAULT now() <br />
    Markdown content goes here.

    </div>
) <br />

CREATE TABLE financas.usuario <br />
( <br />
    id bigint NOT NULL DEFAULT, <br />
    nome character varying(150), <br />
    email character varying(100), <br />
    senha character varying(20), <br />
    data_cadastro date DEFAULT now(), <br />
    CONSTRAINT usuario_pkey PRIMARY KEY (id) <br />
)
