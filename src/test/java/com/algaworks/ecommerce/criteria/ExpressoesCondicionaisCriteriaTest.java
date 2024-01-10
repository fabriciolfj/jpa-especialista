package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.List;

public class ExpressoesCondicionaisCriteriaTest extends EntityManagerTest {

    @Test
    public void usandoExpressaoDistinct() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
        final Root<Pedido> root = query.from(Pedido.class);
        root.join(Pedido_.itemPedidos);

        query.select(root)
                .distinct(true);

        final var type = entityManager.createQuery(query);
        final var result = type.getResultList();

        result.forEach(s -> System.out.println("Pedido " + s.getId()));
    }

    @Test
    public void usandoExpressaoIn() {
        final Cliente cliente01 = new Cliente();
        cliente01.setId(1);

        final Cliente cliente02 = new Cliente();
        cliente02.setId(2);
        final var clients = Arrays.asList(cliente01, cliente02);

        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
        final Root<Pedido> root = query.from(Pedido.class);

        query.select(root)
                .where(root.get(Pedido_.cliente).in(clients));

        final var type = entityManager.createQuery(query);
        final var result = type.getResultList();

        result.forEach(s -> System.out.println("Pedido " + s.getId()));
    }

    @Test
    public void usandoExpressaoLike() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
        final var root = query.from(Cliente.class);

        query.select(root);
        query.where(
                builder.like(root.get("nome"), "%a%")
        );

        final TypedQuery<Cliente> typedQuery = entityManager.createQuery(query);
        final var result = typedQuery.getResultList();

        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void usarIsNull() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        final Root<Produto> root = query.from(Produto.class);

        query.select(root)
                .where(root.get(Produto_.foto).isNull());

        final TypedQuery<Produto> typedQuery = this.entityManager.createQuery(query);
        final var result = typedQuery.getResultList();

        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void usarIsEmpty() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        final Root<Produto> root = query.from(Produto.class);

        query.select(root)
                .where(builder.isEmpty(root.get(Produto_.categorias)));

        final TypedQuery<Produto> typedQuery = this.entityManager.createQuery(query);
        final var result = typedQuery.getResultList();

        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void usarMaiorMenor() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        final Root<Produto> root = query.from(Produto.class);

        query.select(root)
                .where(builder.greaterThanOrEqualTo(root.get(Produto_.PRECO), 799),
                        builder.lessThanOrEqualTo(root.get(Produto_.PRECO), 3500));

        final TypedQuery<Produto> typedQuery = this.entityManager.createQuery(query);
        final var result = typedQuery.getResultList();

        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void usarDataMaiorEMenor() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
        final Root<Pedido> root = query.from(Pedido.class);

        query.select(root)
                .where(builder.greaterThan(root.get(Pedido_.DATA_CRIACAO), LocalDateTime.now().minus(2, ChronoUnit.DAYS)),
                builder.lessThan(root.get(Pedido_.DATA_CRIACAO), LocalDateTime.now().plus(2, ChronoUnit.DAYS)));

        final var type = this.entityManager.createQuery(query);
        final var result = type.getResultList();

        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void usarBetween() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
        final Root<Pedido> root = query.from(Pedido.class);

        query.select(root)
                .where(builder.between(root.get(Pedido_.TOTAL), new BigDecimal(400), new BigDecimal(3000)));

        final var type = this.entityManager.createQuery(query);
        final var result = type.getResultList();

        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void userDiferente() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
        final Root<Pedido> root = query.from(Pedido.class);

        query.select(root)
                .where(builder.notEqual(root.get(Pedido_.TOTAL), new BigDecimal(4999)));

        final var type = this.entityManager.createQuery(query);
        final var result = type.getResultList();

        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void usandoOperadores() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
        final Root<Pedido> root = query.from(Pedido.class);

        query.select(root)
                .where(
                        builder.or(
                                builder.equal(root.get(Pedido_.STATUS), StatusPedido.PAGO),
                                builder.equal(root.get(Pedido_.STATUS), StatusPedido.AGUARDANDO)
                        ), builder.greaterThan(root.get(Pedido_.TOTAL), new BigDecimal(400)));


        final var type = this.entityManager.createQuery(query);
        final var result = type.getResultList();

        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void ordernandoPorNomeCliente() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
        final Root<Cliente> root = query.from(Cliente.class);

        query.select(root)
                .orderBy(builder.asc(root.get(Cliente_.NOME)));

        final var type = this.entityManager.createQuery(query);
        final var result = type.getResultList();

        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void usandoCaseWhen() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        final Root<Pedido> root = query.from(Pedido.class);

        query.multiselect(
                root.get(Pedido_.id),
                /*builder.selectCase(root.get(Pedido_.STATUS))
                        .when(StatusPedido.PAGO.toString(), "foi pago")
                        .when(StatusPedido.CANCELADO.toString(), "foi cancelado")
                        .otherwise(root.get(Pedido_.status))*/
                builder.selectCase(root.get(Pedido_.pagamento).type().as(String.class))
                        .when("boleto", "pago com boleto")
                        .when("cartao", "pago com cartao")
                        .otherwise("nao identificado")

        );

        final var typeQuery = this.entityManager.createQuery(query);
        final var result = typeQuery.getResultList();

        result.forEach(s -> {
            System.out.println(s[0] + " - " + s[1]);
        });
    }
}
