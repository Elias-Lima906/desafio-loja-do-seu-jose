create database oficina_de_carros;

create table pecas_de_carro(
código_de_barras int primary key auto_increment,
nome varchar(50) not null,
modelo_do_carro varchar(50) not null,
fabricante varchar(50) not null,		
preço_de_custo decimal (8, 2) not null,
preço_de_venda decimal (8, 2) not null,
quantidade_em_estoque int not null,
categoria enum ('Motor', 'Suspenção', 'Direção', 'Transmissão', 'Funilaria', 'Outros') not null
)default charset = utf8;

insert into pecapojo 
(codigo_de_barras, nome, modelo_do_carro, fabricante, preço_de_custo, preço_de_venda, quantidade_em_estoque, categoria)
 values 
(2, 'Amortecedor', 'HR-V', 'Honda', 1250.00, 1680.00, 30, 'Suspenção'),
(3, 'Virabrequim', 'HR-V', 'Honda', 350.00, 480.00, 23, 'Motor'), 
(4, 'Semi-eixo', 'HR-V', 'Honda', 750.00, 880.00, 14, 'Transmissão'),
(5, 'Spoiler Dianteiro', 'HR-V', 'Honda', 250.00, 340.00, 7, 'Funilaria'),
(6, 'Adesivos Para Portas', 'HR-V', 'Honda', 48.00, 85.00, 300, 'Outros');

select * from pecapojo;


desc pecapojo;
alter table pecapojo
modify column codigo_de_barras

alter table pecas_de_carro 
rename to estoque_de_pecas;

select nome, quantidade_em_estoque, preço_de_venda from estoque_de_pecas where codigo_de_barras = 1;


0	16	11:12:29	insert into pecapojo values 
 (2, 'Amortecedor', 'HR-V', 'Honda', 1250.00, 1680.00, 30, 'Suspenção'),
 (3, 'Virabrequim', 'HR-V', 'Honda', 350.00, 480.00, 23, 'Motor'), 
 (4, 'Semi-eixo', 'HR-V', 'Honda', 750.00, 880.00, 14, 'Transmissão'),
 (5, 'Spoiler Dianteiro', 'HR-V', 'Honda', 250.00, 340.00, 7, 'Funilaria'),
 (6, 'Adesivos Para Portas', 'HR-V', 'Honda', 48.00, 85.00, 300, 'Outros')	Error Code: 1366. Incorrect integer value: 'Suspenção' for column 'quantidade_em_estoque' at row 1