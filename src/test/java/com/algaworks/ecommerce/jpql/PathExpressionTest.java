package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Test;

import javax.persistence.TypedQuery;

import static org.junit.Assert.assertTrue;

public class PathExpressionTest extends EntityManagerTest {

    @Test
    public void buscarPedidoscomProdutoEspecifico() {
        //var jpql = "Select pi.produto from Pedido p join p.itemPedidos pi join pi.produto pr where pr.id = :parId";
        var jpql = "Select p from Pedido p join fetch p.itemPedidos pi where pi.produto.id = :parId";

        TypedQuery<Pedido> query = entityManager.createQuery(jpql, Pedido.class);
        query.setParameter("parId",1);

        var result = query.getResultList();

        assertTrue(result.size() > 0);
    }
}
