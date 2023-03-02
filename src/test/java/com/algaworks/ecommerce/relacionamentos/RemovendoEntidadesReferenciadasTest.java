package com.algaworks.ecommerce.relacionamentos;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class RemovendoEntidadesReferenciadasTest extends EntityManagerTest {

    @Test
    public void removerEntidadeRelacionada() {
        var pedido = entityManager.find(Pedido.class, 1);

        assertFalse(pedido.getItemPedidos().isEmpty());

        entityManager.getTransaction().begin();
        pedido.getItemPedidos().forEach(i -> entityManager.remove(i));
        entityManager.remove(pedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, 1);
        assertNull(pedidoVerificacao);
    }
}
