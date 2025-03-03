CREATE TABLE usuarios(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    nome TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    cpf VARCHAR(15) NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    role VARCHAR(7) NOT NULL,
    ativo BOOLEAN NOT NULL
);

CREATE TABLE produtos(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    nome TEXT NOT NULL,
    descricao TEXT NOT NULL,
    preco DOUBLE PRECISION NOT NULL,
    data_cadastro DATE NOT NULL,
    estoque INTEGER NOT NULL,
    ativo BOOLEAN NOT NULL
);

CREATE TABLE pedidos(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    data DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    valor_total DOUBLE PRECISION NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE itens_pedidos(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    preco_unitario DOUBLE PRECISION NOT NULL,
    quantidade INTEGER NOT NULL,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    CONSTRAINT fk_pedido FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    CONSTRAINT fk_produto FOREIGN KEY (produto_id) REFERENCES produtos(id)
);
