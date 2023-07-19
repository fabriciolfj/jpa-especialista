package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import org.junit.Test;

import javax.persistence.TypedQuery;

import static junit.framework.TestCase.assertTrue;

public class ExpressoesCondicionaisTest extends EntityManagerTest {

    @Test
    public void usarExpressaoCondicionalLike() {
        var jpql = "Select c From Cliente c where c.nome like concat('%', :nome, '%')";

        var query = this.entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "Medeiros");

        assertTrue(query.getResultList().size() > 0);
    }
}
