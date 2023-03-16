package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EagerELazyTest extends EntityManagerTest {

    @Test
    public void verificarComportamento() {
        var pedido = entityManager.find(Pedido.class, 1);

        assertNotNull(pedido.getCliente().getNome());
        assertTrue(!pedido.getItemPedidos().isEmpty());
    }
}
