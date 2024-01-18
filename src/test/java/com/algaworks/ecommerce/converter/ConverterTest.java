package com.algaworks.ecommerce.converter;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ConverterTest extends EntityManagerTest {

    @Test
    public void testConverter() {
        var produto = new Produto();
        produto.setNome("Dell");
        produto.setPreco(BigDecimal.TEN);
        produto.setDescricao("notebook dell");

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        var result = entityManager.find(Produto.class, produto.getId());

        Assert.assertTrue(result.getAtivo());
    }
}
