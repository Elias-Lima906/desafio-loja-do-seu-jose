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

insert into pecas_de_carro values 
(default, 'Volante', 'HR-V', 'Honda', '250.00', '380.00', '60', 'Direção'),
(default, 'Amortecedor', 'HR-V', 'Honda', '1250.00', '1680.00', '30', 'Suspenção'),
(default, 'Virabrequim', 'HR-V', 'Honda', '350.00', '480.00', '23', 'Motor'), 
(default, 'Semi-eixo', 'HR-V', 'Honda', '750.00', '880.00', '14', 'Transmissão'),
(default, 'Spoiler Dianteiro', 'HR-V', 'Honda', '250.00', '340.00', '7', 'Funilaria'),
(default, 'Adesivos Para Portas', 'HR-V', 'Honda', '48.00', '85.00', '300', 'Outros');

select * from pecas_de_carro;

desc pecas_de_carro;

