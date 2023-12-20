package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class JointTest extends EntityManagerTest {

    @Test
    public void fazerJoinOn() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);

        final Root<Pedido> root = query.from(Pedido.class);
        final Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
        joinPagamento.on(builder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO));

        query.select(root);

        final TypedQuery<Pedido> typedQuery = entityManager.createQuery(query);
        final List<Pedido> result = typedQuery.getResultList();

        Assert.assertFalse(result.isEmpty());
        Assert.assertTrue(result.size() == 1);
    }

    @Test
    public void fazerJoin() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);

        final Root<Pedido> root = query.from(Pedido.class);
        final Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
        final Join<Pedido, ItemPedido> joinItemPedido = root.join("itemPedidos");
        final Join<ItemPedido, Produto> joinProduto = joinItemPedido.join("produto");

        query.select(root);
        query.where(builder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO));

        final TypedQuery<Pedido> typedQuery = entityManager.createQuery(query);
        final List<Pedido> result = typedQuery.getResultList();

        Assert.assertFalse(result.isEmpty());
    }
}
