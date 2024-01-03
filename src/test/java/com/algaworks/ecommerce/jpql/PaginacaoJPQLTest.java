package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import org.junit.Test;

import javax.persistence.TypedQuery;

public class PaginacaoJPQLTest extends EntityManagerTest {

    @Test
    public void paginarResultados() {
        var jpql = "select c from Categoria c order by c.nome";
        TypedQuery<Categoria> typedQuery = entityManager.createQuery(jpql, Categoria.class);

        typedQuery.setFirstResult(0);
        typedQuery.setMaxResults(2000);

        var result = typedQuery.getResultList();

        result.forEach(s -> System.out.println(s.getId() + " " + s.getNome()));

    }
}
