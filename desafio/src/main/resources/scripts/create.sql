drop database if exists desafio_padawan;
create database desafio_padawan;
use desafio_padawan;

create table produto(
	id_produto bigint primary key auto_increment,
    nome varchar(255) not null,
    descricao varchar(255) not null,
    tamanho varchar(2) not null,
    preco decimal(5,2) not null,
    url varchar(255) default '',
    diretorio varchar(255) default ''
); 

create table cliente(
    id_cliente bigint primary key auto_increment,
    nome varchar(255) not null
);

create table funcionario(
	id_funcionario bigint primary key auto_increment,
    nome varchar(255) not null,
    dono boolean not null default 0
);

create table movimento (
	id_movimento bigint primary key auto_increment,
    codigo_inicial varchar(255) not null,
    codigo_rastreio varchar(255) default '',
    data date not null,
    status varchar(1),
    produto bigint,
    funcionario bigint,
    cliente bigint
);

create table loja(
    id_loja bigint primary key auto_increment,
    nome varchar(100) not null,
    funcionario bigint
);

alter table produto add constraint chk_tamanho check (tamanho in ('P', 'M', 'G', 'GG'));
alter table movimento add constraint chk_status check (status in ('E', 'S', 'D', 'C')); -- E -> Entregue, S-> Saiu para entrega, D -> Devolvido, C -> Cancelado
alter table movimento add constraint fk_movimento_colaborador foreign key (funcionario) references funcionario (id_funcionario);
alter table movimento add constraint fk_movimento_produto foreign key (produto) references produto (id_produto);
alter table movimento add constraint fk_movimento_cliente foreign key (cliente) references cliente (id_cliente);
alter table loja add constraint fk_loja_funcionario foreign key (funcionario) references funcionario(id_funcionario);