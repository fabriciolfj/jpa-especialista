package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;

public class JointTest extends EntityManagerTest {

    @Test
    public void fazerLeftJoin() {
        final String jpql = "select p from Pedido p left join p.pagamento pag";
        TypedQuery<Pedido> query = this.entityManager.createQuery(jpql, Pedido.class);

        var result = query.getResultList();

        Assert.assertTrue(result.size() == 2);
    }


    @Test
    public void fazerJoin() {
        final String jpql = "select p From Pedido p join p.pagamento pag on pag.pedido = p";

        TypedQuery<Pedido> typedQuery = this.entityManager.createQuery(jpql, Pedido.class);
        var result = typedQuery.getResultList();

        Assert.assertTrue(result.size() == 1);
    }
}
