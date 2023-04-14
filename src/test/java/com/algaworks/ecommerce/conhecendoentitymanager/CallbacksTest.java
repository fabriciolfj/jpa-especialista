package com.algaworks.ecommerce.conhecendoentitymanager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CallbacksTest extends EntityManagerTest {

    @Test
    public void testCallback() {
        final Cliente cliente = entityManager.find(Cliente.class, 1);
        final Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(BigDecimal.TEN);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);

        //Pedido pedidoPago = entityManager.find(Pedido.class, pedido.getId());
        pedido.setStatus(StatusPedido.PAGO);
        entityManager.flush();

        Pedido pedidoPago = entityManager.find(Pedido.class, pedido.getId());
        assertTrue(pedidoPago.getStatus() == StatusPedido.PAGO);
        assertNotNull(pedidoPago.getDataCriacao());
        assertNotNull(pedidoPago.getDataUltimaAtualizacao());

        entityManager.getTransaction().commit();
    }
}
