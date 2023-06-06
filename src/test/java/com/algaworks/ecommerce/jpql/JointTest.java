package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;

public class JointTest extends EntityManagerTest {

    //para resolver o problema de n + 1, ou seja, para cada registro ele nao vai mas buscar no banco, vai trazer tudo na mesmo consulta graças ao join fetch
    @Test
    public void userJoinFetch() {
        final String jpql = "select p from Pedido p"
                + " left join fetch p.pagamento"
                + " join fetch p.cliente"
                + " left join fetch p.notafiscal";

        TypedQuery<Pedido> query = entityManager.createQuery(jpql, Pedido.class);
        final var pedidos = query.getResultList();

        Assert.assertEquals(2, pedidos.size());
    }

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
