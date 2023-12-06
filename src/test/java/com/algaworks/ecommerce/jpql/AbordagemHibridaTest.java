package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class AbordagemHibridaTest extends EntityManagerTest {

    @BeforeClass
    public static void setUpBeforeClass() {
        var factory = entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");

        final EntityManager em = factory.createEntityManager();
        final String jqpl = "Select c from Categoria c";
        var type = em.createQuery(jqpl, Categoria.class);

        factory.addNamedQuery("Categoria.listar", type);
    }

    @Test
    public void testListarCategorias() {
        var query = this.entityManager.createNamedQuery("Categoria.listar", Categoria.class);
        var result = query.getResultList();

        Assert.assertFalse(result.isEmpty());
    }
}
