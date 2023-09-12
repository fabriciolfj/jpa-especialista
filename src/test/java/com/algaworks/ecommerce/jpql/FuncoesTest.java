package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;

public class FuncoesTest extends EntityManagerTest {

    @Test
    public void executeFuncaoNativa() {
        final String jpql = "select function('dayname', e.dataCriacao) from Pedido e " +
                "where function('acima_media_faturamento', e.total)  = 1";

        TypedQuery<String> query = this.entityManager.createQuery(jpql, String.class);

        var result = query.getResultList();
        Assert.assertFalse(result.isEmpty());

        result.forEach(System.out::println);
    }
}
