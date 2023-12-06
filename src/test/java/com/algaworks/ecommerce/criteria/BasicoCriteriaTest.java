package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class BasicoCriteriaTest extends EntityManagerTest {

    @Test
    public void buscarPorIdentificador() {
        final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        final Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 7));

        final TypedQuery<Pedido> query = this.entityManager.createQuery(criteriaQuery);
        final var result = query.getResultList();

        Assert.assertFalse(result.isEmpty());
    }
}
