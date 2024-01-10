package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.Test;

import javax.persistence.criteria.*;
import java.math.BigDecimal;

public class SubqueriesCriteriaTest extends EntityManagerTest {

    //vendeu pelo menos uma vez o produto com o preco atual
    @Test
    public void pesquisaSubquerieAny() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        final Root<Produto> root = query.from(Produto.class);

        final Subquery<BigDecimal> subquery = query.subquery(BigDecimal.class);
        final Root<ItemPedido> itemPedidoRoot = subquery.from(ItemPedido.class);
        subquery.select(itemPedidoRoot.get(ItemPedido_.precoProduto));
        subquery.where(builder.equal(itemPedidoRoot.get(ItemPedido_.produto), root));

        query.where(builder.equal(
                        root.get(Produto_.preco), builder.any(subquery))
        );

        final var type = entityManager.createQuery(query);
        final var result = type.getResultList();

        result.forEach(s -> System.out.println("Produto " + s.getId()));
    }

    @Test
    public void pesquisaSubquerieAll02() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        final Root<Produto> root = query.from(Produto.class);

        final Subquery<BigDecimal> subquery = query.subquery(BigDecimal.class);
        final Root<ItemPedido> itemPedidoRoot = subquery.from(ItemPedido.class);
        subquery.select(itemPedidoRoot.get(ItemPedido_.precoProduto));
        subquery.where(builder.equal(itemPedidoRoot.get(ItemPedido_.produto), root));

        query.where(builder.greaterThan(
                root.get(Produto_.preco), builder.all(subquery)),
                builder.exists(subquery)
        );

        final var type = entityManager.createQuery(query);
        final var result = type.getResultList();

        result.forEach(s -> System.out.println("Produto " + s.getId()));
    }

    @Test
    public void pesquisaSubquerieAll() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        final Root<Produto> root = query.from(Produto.class);

        final Subquery<BigDecimal> subquery = query.subquery(BigDecimal.class);
        final Root<ItemPedido> itemPedidoRoot = subquery.from(ItemPedido.class);
        subquery.select(itemPedidoRoot.get(ItemPedido_.precoProduto));
        subquery.where(builder.equal(itemPedidoRoot.get(ItemPedido_.produto), root));

        query.where(builder.equal(
                root.get(Produto_.preco), builder.all(subquery)),
                builder.exists(subquery));

        final var type = entityManager.createQuery(query);
        final var result = type.getResultList();

        result.forEach(s -> System.out.println("Produto " + s.getId()));
    }

    @Test
    public void pesquisaSubquerieExists() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        final Root<Produto> root = query.from(Produto.class);

        final Subquery<Integer> subquery = query.subquery(Integer.class);
        final Root<ItemPedido> itemPedidoRoot = subquery.from(ItemPedido.class);
        subquery.select(builder.literal(1));
        subquery.where(builder.equal(itemPedidoRoot.get(ItemPedido_.PRODUTO), root));

        query.where(builder.exists(subquery));

        final var type = entityManager.createQuery(query);
        final var result = type.getResultList();

        result.forEach(s -> System.out.println("Produto " + s.getId()));
    }

    @Test
    public void pesquisaSubquerie4() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
        final Root<Pedido> root = query.from(Pedido.class);
        query.select(root);

        final Subquery<Integer> subquery = query.subquery(Integer.class);
        final Root<ItemPedido> subQueryRoot = subquery.from(ItemPedido.class);
        final Join<ItemPedido, Produto> joinProduto = subQueryRoot.join(ItemPedido_.produto);
        final Join<ItemPedido, Pedido> joinPedido = subQueryRoot.join(ItemPedido_.pedido);
        subquery.select(joinPedido.get(Pedido_.id))
                .where(
                        builder.greaterThan(joinProduto.get(Produto_.preco), new BigDecimal(100.00))
                );

        query.where(root.get(Pedido_.id).in(subquery));

        final var type = entityManager.createQuery(query);
        final var result = type.getResultList();

        result.forEach(s -> System.out.println("Pedido " + s.getId()));
    }

    @Test
    public void pesquisaSubqueries3() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
        final Root<Cliente> root = query.from(Cliente.class);
        query.select(root);

        final Subquery<BigDecimal> subquery = query.subquery(BigDecimal.class);
        final Root<Pedido> subQueryRoot = subquery.from(Pedido.class);
        subquery.select(builder.sum(subQueryRoot.get(Pedido_.total)).as(BigDecimal.class))
                .where(
                        builder.equal(root.get(Cliente_.id), subQueryRoot.get(Pedido_.cliente).get(Cliente_.id))
                );

        query.where(builder.lessThan(subquery, new BigDecimal(500)));

        final var type = entityManager.createQuery(query);
        final var result = type.getResultList();

        result.forEach(s -> System.out.println("Cliente " + s.getId()));
    }

    @Test
    public void pesquisaSubqueries2() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
        final Root<Pedido> root = query.from(Pedido.class);
        query.select(root);

        final Subquery<BigDecimal> subquery = query.subquery(BigDecimal.class);
        final Root<Pedido> subQueryRoot = subquery.from(Pedido.class);
        subquery.select(builder.avg(subQueryRoot.get(Pedido_.total)).as(BigDecimal.class));

        query.where(builder.greaterThan(root.get(Pedido_.TOTAL), subquery));

        final var type = entityManager.createQuery(query);
        final var result = type.getResultList();

        result.forEach(s -> System.out.println("Pedido " + s.getId()));
    }

    @Test
    public void pesquisaSubqueries1() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        final Root<Produto> root = query.from(Produto.class);
        query.select(root);

        final Subquery<BigDecimal> subquery = query.subquery(BigDecimal.class);
        final Root<Produto> subQueryRoot = subquery.from(Produto.class);
        subquery.select(builder.max(subQueryRoot.get(Produto_.preco)));

        query.where(builder.equal(root.get(Produto_.PRECO), subquery));

        final var type = entityManager.createQuery(query);
        final var result = type.getResultList();

        result.forEach(s -> System.out.println("Produto " + s.getId()));
    }

    @Test
    public void pegarClientesComPedidos() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
        final Root<Cliente> root = query.from(Cliente.class);

        final Subquery<Integer> subquery = query.subquery(Integer.class);
        final Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
        final Join<Pedido, Cliente> join = subqueryRoot.join(Pedido_.CLIENTE);
        subquery.select(subqueryRoot.get(Pedido_.CLIENTE));

        query.select(root)
                .where(root.get(Cliente_.ID).in(subquery));

        final var type = this.entityManager.createQuery(query);
        final var result = type.getResultList();

        result.forEach(s ->  System.out.println("Cliente  " + s.getId()));



    }
}
