package com.algaworks.ecommerce.operacaoemcascata;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void testRemovePedidoAItemOrphan() {
        final var pedido = entityManager.find(Pedido.class, 7 );
        assertFalse(pedido.getItemPedidos().isEmpty());

        entityManager.getTransaction().begin();
        pedido.getItemPedidos().remove(0);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoCheck = entityManager.find(Pedido.class, pedido.getId());
        assertTrue(pedidoCheck.getItemPedidos().isEmpty());
    }

    @Test
    public void testRemoveCategoriasProduto() {
        final Produto produto = entityManager.find(Produto.class, 3);
        assertFalse(produto.getCategorias().isEmpty());

        entityManager.getTransaction().begin();
        produto.getCategorias().clear();
        entityManager.getTransaction().commit();
        entityManager.clear();

        final Produto check = entityManager.find(Produto.class, 3);
        assertTrue(check.getCategorias().isEmpty());
    }
}
