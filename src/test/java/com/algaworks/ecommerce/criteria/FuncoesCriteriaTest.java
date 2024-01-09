package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

public class FuncoesCriteriaTest extends EntityManagerTest {

    @Test
    public void aplicarFuncaoString() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.multiselect(
                root.get(Cliente_.nome),
                criteriaBuilder.concat("Nome do cliente: ", root.get(Cliente_.nome)),
                criteriaBuilder.length(root.get(Cliente_.nome)),
                criteriaBuilder.locate(root.get(Cliente_.nome), "a"),
                criteriaBuilder.substring(root.get(Cliente_.nome), 1, 2),
                criteriaBuilder.lower(root.get(Cliente_.nome)),
                criteriaBuilder.upper(root.get(Cliente_.nome)),
                criteriaBuilder.trim(root.get(Cliente_.nome))
        );

        criteriaQuery.where(criteriaBuilder.equal(
                criteriaBuilder.substring(root.get(Cliente_.nome), 1, 1), "M"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(
                arr[0]
                        + ", concat: " + arr[1]
                        + ", length: " + arr[2]
                        + ", locate: " + arr[3]
                        + ", substring: " + arr[4]
                        + ", lower: " + arr[5]
                        + ", upper: " + arr[6]
                        + ", trim: |" + arr[7] + "|"));
    }

    @Test
    public void aplicandoFuncaoData() {
        final var builder = this.entityManager.getCriteriaBuilder();
        final var query = builder.createQuery(Object[].class);
        final var root = query.from(Pedido.class);
        final Join<Pedido, Pagamento> joinPagamento = root.join(Pedido_.pagamento);
        final Join<Pedido, PagamentoBoleto> joinPagamentoBoleto = builder.treat(joinPagamento, PagamentoBoleto.class);

        query.multiselect(
                root.get(Pedido_.id),
                builder.currentDate(),
                builder.currentTime(),
                builder.currentTimestamp()
        );

        query.where(
                builder.between(builder.currentDate(),
                        root.get(Pedido_.DATA_CRIACAO).as(java.sql.Date.class),
                        joinPagamentoBoleto.get(PagamentoBoleto_.DATA_VENCIMENTO).as(java.sql.Date.class)
                        ),
                builder.equal(root.get(Pedido_.STATUS), StatusPedido.AGUARDANDO)
        );

        final TypedQuery<Object[]> type = this.entityManager.createQuery(query);
        final var result = type.getResultList();
        Assert.assertFalse(result.isEmpty());

        result.forEach(a ->
                System.out.println(a[0] + ", current date: " + a[1] + ", current time: " + a[2] + ", current timestamp: " + a[3]));

    }

    @Test
    public void aplicacaoFuncaoNumerica() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        final Root<Pedido> root = query.from(Pedido.class);

        query.multiselect(
                builder.abs(builder.prod(root.get(Pedido_.id), -1)),
                builder.sqrt(root.get(Pedido_.total)),
                builder.mod(root.get(Pedido_.id), 2)
        );

        query.where(builder.greaterThan(builder.sqrt(root.get(Pedido_.total)), 10.0));

        final TypedQuery<Object[]> typedQuery = this.entityManager.createQuery(query);
        final var result = typedQuery.getResultList();
        result.forEach(v -> {
            System.out.println("abs: " + v[0] + " ,sqrt: " + v[1] + " ,mod: " + v[2]);
        });
    }

    @Test
    public void aplicacaoFuncaoSize() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        final Root<Pedido> root = query.from(Pedido.class);

        query.multiselect(
                root.get(Pedido_.ID),
                builder.size(root.get(Pedido_.ITEM_PEDIDOS))
        );

        query.where(builder.greaterThan(builder.size(root.get(Pedido_.ITEM_PEDIDOS)), 1));

        final TypedQuery<Object[]> typedQuery = this.entityManager.createQuery(query);
        final var result = typedQuery.getResultList();
        result.forEach(v -> {
            System.out.println("pedido: " + v[0] + " ,size: " + v[1]);
        });
    }

    @Test
    public void aplicarFuncaoNativas() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.multiselect(
                root.get(Pedido_.id),
                criteriaBuilder.function("dayname", String.class, root.get(Pedido_.dataCriacao))
        );

        criteriaQuery.where(criteriaBuilder.isTrue(
                criteriaBuilder.function(
                        "acima_media_faturamento", Boolean.class, root.get(Pedido_.total))));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(
                arr[0] + ", dayname: " + arr[1]));
    }

    @Test
    public void aplicandoFuncaoAgrupamento() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        final Root<Pedido> root = query.from(Pedido.class);

        query.multiselect(
                builder.avg(root.get(Pedido_.total)),
                builder.count(root.get(Pedido_.total)),
                builder.sum(root.get(Pedido_.total)),
                builder.min(root.get(Pedido_.total)),
                builder.max(root.get(Pedido_.total))
        );


        final TypedQuery<Object[]> typedQuery = this.entityManager.createQuery(query);
        final var result = typedQuery.getResultList();
        result.forEach(v -> {
            System.out.println("avg: "
                    + v[0] + " ,count: "
                    + v[1] + " ,sum: "
                    + v[2] + " ,min: "
                    + v[3] + " ,max: "
                    + v[4]);
        });
    }

    @Test
    public void aplicandoGroupBy() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        final Root<Categoria> categoriaRoot = query.from(Categoria.class);
        final Join<Categoria, Produto> joinProduto = categoriaRoot.join(Categoria_.PRODUTOS, JoinType.LEFT);

        query.multiselect(
                categoriaRoot.get(Categoria_.NOME),
                builder.count(joinProduto.get(Produto_.ID))
        );

        query.groupBy(categoriaRoot.get(Categoria_.id));

        final var type = this.entityManager.createQuery(query);
        final var result = type.getResultList();

        result.forEach(v -> System.out.println("categoria: " + v[0] + " ,qtde produto: " + v[1]));
    }

    @Test
    public void aplicandoGroupBy02() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        final Root<ItemPedido> itemPedidoRoot = query.from(ItemPedido.class);
        final Join<ItemPedido, Produto> produtoJoin = itemPedidoRoot.join(ItemPedido_.PRODUTO);
        final Join<Produto, Categoria> categoriaJoin = produtoJoin.join(Produto_.categorias);

        query.multiselect(
                categoriaJoin.get(Categoria_.NOME),
                builder.sum(itemPedidoRoot.get(ItemPedido_.PRECO_PRODUTO))
        );

        query.groupBy(categoriaJoin.get(Categoria_.id));

        final var type = this.entityManager.createQuery(query);
        final var result = type.getResultList();

        result.forEach(v -> System.out.println("categoria: " + v[0] + " ,total: " + v[1]));
    }

    @Test
    public void agruparResultadoComFuncoes() {
//         Total de vendas por mês.
//        String jpql = "select concat(year(p.dataCriacao), '/', function('monthname', p.dataCriacao)), sum(p.total) " +
//                " from Pedido p " +
//                " group by year(p.dataCriacao), month(p.dataCriacao) ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        Expression<Integer> anoCriacaoPedido = criteriaBuilder
                .function("year", Integer.class, root.get(Pedido_.dataCriacao));
        Expression<Integer> mesCriacaoPedido = criteriaBuilder
                .function("month", Integer.class, root.get(Pedido_.dataCriacao));
        Expression<String> nomeMesCriacaoPedido = criteriaBuilder
                .function("monthname", String.class, root.get(Pedido_.dataCriacao));

        Expression<String> anoMesConcat = criteriaBuilder.concat(
                criteriaBuilder.concat(anoCriacaoPedido.as(String.class), "/"),
                nomeMesCriacaoPedido
        );

        criteriaQuery.multiselect(
                anoMesConcat,
                criteriaBuilder.sum(root.get(Pedido_.total))
        );

        criteriaQuery.groupBy(anoCriacaoPedido, mesCriacaoPedido);

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        lista.forEach(arr -> System.out.println("Ano/Mês: " + arr[0] + ", Sum: " + arr[1]));
    }

    @Test
    public void condicionarAgrupamentoComHaving() {
//         Total de vendas dentre as categorias que mais vendem.
//        String jpql = "select cat.nome, sum(ip.precoProduto) from ItemPedido ip " +
//                " join ip.produto pro join pro.categorias cat " +
//                " group by cat.id " +
//                " having sum(ip.precoProduto) > 100 ";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);
        Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.produto);
        Join<Produto, Categoria> joinProdutoCategoria = joinProduto.join(Produto_.categorias);

        criteriaQuery.multiselect(
                joinProdutoCategoria.get(Categoria_.nome),
                criteriaBuilder.sum(root.get(ItemPedido_.precoProduto)),
                criteriaBuilder.avg(root.get(ItemPedido_.precoProduto))
        );

        criteriaQuery.groupBy(joinProdutoCategoria.get(Categoria_.id));

        criteriaQuery.having(criteriaBuilder.greaterThan(
                criteriaBuilder.avg(
                        root.get(ItemPedido_.precoProduto)).as(BigDecimal.class),
                new BigDecimal(700)));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        lista.forEach(arr -> System.out.println(
                "Nome categoria: " + arr[0]
                        + ", SUM: " + arr[1]
                        + ", AVG: " + arr[2]));
    }
}
