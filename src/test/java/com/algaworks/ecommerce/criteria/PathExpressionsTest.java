package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente_;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Pedido_;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PathExpressionsTest extends EntityManagerTest {

    @Test
    public void usarPathExpression() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
        final Root<Pedido> root = query.from(Pedido.class);

        query.select(root);
        query.where(builder.like(root.get(Pedido_.CLIENTE).get(Cliente_.NOME), "M%"));

        final var type = this.entityManager.createQuery(query);
        final var result = type.getResultList();

        Assert.assertFalse(result.isEmpty());
    }
}
