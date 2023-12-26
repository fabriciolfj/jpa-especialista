package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class ExpressoesCondicionaisCriteriaTest extends EntityManagerTest {

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
}
