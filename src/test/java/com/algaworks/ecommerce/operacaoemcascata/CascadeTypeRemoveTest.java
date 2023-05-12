package com.algaworks.ecommerce.operacaoemcascata;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class CascadeTypeRemoveTest extends EntityManagerTest {

    @Test
    public void testRemovePedidoAndItem() {
        final var pedido = entityManager.find(Pedido.class, 7 );

        entityManager.getTransaction().begin();
        entityManager.remove(pedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoCheck = entityManager.find(Pedido.class, pedido.getId());
        assertNull(pedidoCheck);
    }
}
