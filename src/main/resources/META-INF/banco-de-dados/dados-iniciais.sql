insert into produto (id, nome, preco,data_criacao, descricao) values (1, 'Kindle', 499.0, date_sub(sysdate(), interval 1 day), 'Conheça o novo Kindle, agora com iluminação embutida ajustável, que permite que você leia em ambientes abertos ou fechados, a qualquer hora do dia.');
insert into produto (id, nome, preco,data_criacao, descricao) values (3, 'Câmera GoPro Hero 7', 1400.0, date_sub(sysdate(), 'Desempenho 2x melhor.');

insert into cliente (id, nome) values (1, 'Fernando Medeiros');
insert into cliente (id, nome) values (2, 'Marcos Mariano');

insert into pedido (data_conclusao, data_criacao, data_ultima_atualizacao, Logradouro, bairro, cep, cidade, complemento, estado, numero, status, total, cliente_id) values (sysdate(), sysdate(), sysdate(),'Logradouro', 'bairro', '01201-030','SÃO-PAULO', 'COMPLEMENTO','SÃO PAULO', 100, 'AGUARDANDO', 100.0, 1);

insert into item_pedido (pedido_id, produto_id, preco_produto, quantidade) values (1, 1, 5.0, 2);

insert into categoria(id, nome) values (1, 'Eletronicos');