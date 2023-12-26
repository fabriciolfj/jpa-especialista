package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.Produto_;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class MetaModelTest extends EntityManagerTest {

    @Test
    public void utilizarMetaModel() {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        final Root<Produto> root = query.from(Produto.class);

        query.select(root);
        query.where(builder.or(
                builder.like(root.get(Produto_.NOME), "%C%"),
                builder.like(root.get(Produto_.DESCRICAO), "%C%")
        ));

        final TypedQuery<Produto> typedQuery = entityManager.createQuery(query);
        final var result = typedQuery.getResultList();

        Assert.assertFalse(result.isEmpty());
    }
}
