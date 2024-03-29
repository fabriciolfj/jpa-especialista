insert into produto (id, nome, preco, data_criacao, descricao, ativo) values (1, 'Kindle', 199.0, date_sub(sysdate(), interval 1 day), 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.', 'SIM');
insert into produto (id, nome, preco, data_criacao, descricao, ativo) values (3, 'Câmera GoPro Hero 7', 1400, date_sub(sysdate(), interval 1 day), 'Desempenho 2x melhor.', 'SIM');
insert into produto (id, nome, preco, data_criacao, descricao, ativo) values (4, 'janela', 40.0, date_sub(sysdate(), interval 1 day), 'Desempenho 2x melhor.', 'NAO');

insert into cliente (id, nome, cpf) values (1, 'Fernando Medeiros', '000');
insert into cliente (id, nome, cpf) values (2, 'Marcos Mariano', '111');
insert into cliente (id, nome, cpf) values (3, 'fabricio jacob', '12211');

insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (1, 'MASCULINO', date_sub(sysdate(), interval 27 year));
insert into cliente_detalhe (cliente_id, sexo, data_nascimento) values (2, 'MASCULINO', date_sub(sysdate(), interval 30 year));

insert into pedido (id, cliente_id, data_criacao, total, status) values (7, 1, date_sub(sysdate(), interval 5 day), 2998.0, 'AGUARDANDO');
insert into pedido (id, cliente_id, data_criacao, total, status) values (2, 1, sysdate(), 499.0, 'PAGO');
insert into pedido (id, cliente_id, data_criacao, total, status) values (9, 3, sysdate(), 499.0, 'CANCELADO');

insert into pedido (id, cliente_id, data_criacao, total, status) values (71, 2, date_sub(sysdate(), interval 5 day), 2998.0, 'AGUARDANDO');
insert into pedido (id, cliente_id, data_criacao, total, status) values (255, 2, sysdate(), 178.0, 'AGUARDANDO');


insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (7, 3, 1400, 2);
insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (2, 1, 499, 1);

insert into pagamento (pedido_id, status, tipo_pagamento, numero_cartao, codigo_barras) values (2, 'PROCESSANDO', 'cartao', '123', null);
insert into pagamento (pedido_id, status, tipo_pagamento, numero_cartao, codigo_barras, data_vencimento) values (9, 'AGUARDANDO', 'boleto', '123', null, date_add(sysdate(), interval 2 day));

insert into nota_fiscal (pedido_id, xml, data_emissao) values (2, '<xml>', sysdate())

insert into categoria (nome) values ('Eletrodomésticos');
insert into categoria (nome) values ('Livros');
insert into categoria (nome) values ('Esportes');
insert into categoria (nome) values ('Futebol');
insert into categoria (nome) values ('Natação');
insert into categoria (nome) values ('Notebooks');
insert into categoria (nome) values ('Smartphones');

insert into produto_categoria (produto_id, categoria_id) values (1, 6);
insert into produto_categoria (produto_id, categoria_id) values (3, 2);



--create table testando (id integer not null auto_increment, primary key (id)) engine=InnoDB;

--create function acima_media_faturamento(valor double) returns boolean reads sql data return valor > (select avg(total) from pedido);