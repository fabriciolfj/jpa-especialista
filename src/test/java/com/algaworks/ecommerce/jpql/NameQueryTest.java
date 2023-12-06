package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

public class NameQueryTest extends EntityManagerTest {

    @Test
    public void testQueryXmlV2() {
        var query = this.entityManager.createNamedQuery("Pedido.todos", Pedido.class);
        var result = query.getResultList();

        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void testQueryXml() {
        var query = this.entityManager.createNamedQuery("Pedido.listar", Pedido.class);
        var result = query.getResultList();

        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void testNamedQuery() {
        var query = this.entityManager.createNamedQuery("Produto.listaCategoria");
        query.setParameter("categoria", 2);

        var result = query.getResultList();

        Assert.assertFalse(result.isEmpty());
    }
}
