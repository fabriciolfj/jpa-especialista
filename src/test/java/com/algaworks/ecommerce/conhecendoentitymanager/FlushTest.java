package com.algaworks.ecommerce.conhecendoentitymanager;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FlushTest extends EntityManagerTest {

    @Test
    public void testFlush() {
        try {
            entityManager.getTransaction().begin();

            final Pedido pedido = entityManager.find(Pedido.class, 1);
            pedido.setStatus(StatusPedido.PAGO);

            /*entityManager.flush();

            if (pedido.getPagamento() == null) {
                throw new RuntimeException();
            }*/

            //para query o jpa precisa sincronizar a memoria com o banco, como se executa-se o flush antes
            final Pedido pedidoPago = entityManager.createQuery("Select p from Pedido p where p.id=1", Pedido.class)
                            .getSingleResult();

            assertTrue(pedido.getStatus() == StatusPedido.PAGO);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
}
