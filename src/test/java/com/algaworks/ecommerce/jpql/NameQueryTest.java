package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

public class NameQueryTest extends EntityManagerTest {

    @Test
    public void testNamedQuery() {
        var query = this.entityManager.createNamedQuery("Produto.listaCategoria");
        query.setParameter("categoria", 2);

        var result = query.getResultList();

        Assert.assertFalse(result.isEmpty());
    }
}
