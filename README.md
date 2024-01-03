# jpa-especialista

# dicas

## optional
- false ele vai usar um inner join
- true (padrao) utiliza um left outer join

## MapsId
- quando um atributo da classe pode ser chave estrangeira e primary key
- podemos combinar com EmbeddedId, para chave composta

## ElementCollection
- quando vamos salvar uma lista apenas


## Treat
```
final Join<Pedido, Pagamento> joinPagamento = root.join(Pedido_.pagamento);
final Join<Pedido, PagamentoBoleto> joinPagamentoBoleto = builder.treat(joinPagamento, PagamentoBoleto.class);
```
- na situação acima estamos efetuando o join do pedido com pagamento
- no entanto quero usar pagamento boleto que extende pagamento
- utilizo o treat
- lembrando:
  - o join do Pedido com Pagamento, 
  - no root referencio qual atributo do pedido que faz o vinculo, e nesse join que terei acessos aos fields do pagamento
```
joinPagamentoBoleto.get(PagamentoBoleto_.DATA_VENCIMENTO).as(java.sql.Date.class) //uso no where da query
```