package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import static org.junit.Assert.assertNotNull;

public class BasicoJPQLTest extends EntityManagerTest {

    @Test
    public void buscaPorIdentificador() {
        final TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p where p.id = 7", Pedido.class);

        final Pedido pedido = typedQuery.getSingleResult();
        assertNotNull(pedido);
    }

    @Test
    public void mostrarDiferencaQueryTypedQuery() {
        final var jpql = "select p from Pedido p where p.id = 7";

        final TypedQuery<Pedido> typed = entityManager.createQuery(jpql, Pedido.class);
        final var result = typed.getSingleResult();

        assertNotNull(result);

        final Query query = entityManager.createQuery(jpql);

        final Pedido pedido = (Pedido) query.getSingleResult();
        assertNotNull(pedido);
    }
}
