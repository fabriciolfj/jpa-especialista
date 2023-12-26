package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

import static org.junit.Assert.*;

public class JointTest extends EntityManagerTest {

    @Test
    public void buscandoProdutoEspecificoDentroDoPedido() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Produto> query = builder.createQuery(Produto.class);

        final Root<Pedido> root = query.from(Pedido.class);
        final Join<ItemPedido, Produto> joinProduto = root.join(Pedido_.ITEM_PEDIDOS)
                .join(ItemPedido_.PRODUTO);
        joinProduto.on(builder.equal(joinProduto.get("id"), 3));

        query.select(joinProduto);

        final var type = entityManager.createQuery(query);
        final var result = type.getResultList();

        assertFalse(result.isEmpty());
    }

    @Test
    public void testJoinFetch() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);

        final var root = query.from(Pedido.class);
        //root.fetch("itemPedidos");
        root.fetch("cliente");
        root.fetch("notafiscal", JoinType.LEFT);
        root.fetch("pagamento", JoinType.LEFT);
        //Join<Pedido, Cliente> joinClient = (Join<Pedido, Cliente>) root.<Pedido, Cliente>fetch("cliente");

        query.select(root).where(builder.equal(root.get("id"), 7));

        var type = entityManager.createQuery(query);
        var result = type.getSingleResult();

        assertNotNull(result);
        //assertFalse(result.getItemPedidos().isEmpty());
    }

    @Test
    public void fazerLeftJoinOn() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);

        final Root<Pedido> root = query.from(Pedido.class);
        final Join<Pedido, Pagamento> joinPagamento = root.join("pagamento", JoinType.LEFT);

        query.select(root);

        final TypedQuery<Pedido> typedQuery = entityManager.createQuery(query);
        final List<Pedido> result = typedQuery.getResultList();

        assertFalse(result.isEmpty());
        Assert.assertTrue(result.size() == 5);
    }

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

        assertFalse(result.isEmpty());
        Assert.assertTrue(result.size() == 1);
    }

    @Test
    public void fazerJoinOnPegandoPagamento() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pagamento> query = builder.createQuery(Pagamento.class);

        final Root<Pedido> root = query.from(Pedido.class);
        final Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
        joinPagamento.on(builder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO));

        query.select(joinPagamento);

        final TypedQuery<Pagamento> typedQuery = entityManager.createQuery(query);
        final List<Pagamento> result = typedQuery.getResultList();

        assertFalse(result.isEmpty());
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

        assertFalse(result.isEmpty());
    }
}
