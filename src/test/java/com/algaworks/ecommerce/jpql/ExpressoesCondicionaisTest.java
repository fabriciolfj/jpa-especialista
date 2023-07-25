package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Test;

import javax.persistence.TypedQuery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class ExpressoesCondicionaisTest extends EntityManagerTest {

    @Test
    public void usarMaiorMenorData() {
        final String jpql = "Select p from Pedido p where p.dataCriacao >= :data";
        final TypedQuery<Pedido> query = this.entityManager.createQuery(jpql, Pedido.class);
        query.setParameter("data", LocalDateTime.now().minus(2, ChronoUnit.DAYS));

        var result = query.getResultList();

        assertTrue(result.size() == 1);
    }

    @Test
    public void usarMaiorMenor() {
        String jpql = "Select p from Produto p where p.preco >= :precoInicial and p.preco <= :precoFinal";
        var query = this.entityManager.createQuery(jpql, Produto.class);
        query.setParameter("precoInicial", new BigDecimal(499));
        query.setParameter("precoFinal", new BigDecimal(1500));

        var result = query.getResultList();

        assertFalse(result.isEmpty());
    }

    @Test
    public void usarExpressaoCondicionalLike() {
        var jpql = "Select c From Cliente c where c.nome like concat('%', :nome, '%')";

        var query = this.entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "Medeiros");

        assertTrue(query.getResultList().size() > 0);
    }

    @Test
    public void userIsNull() {
        var jpql = "select p from Produto p where p.foto is null";
        TypedQuery<Produto> query = this.entityManager.createQuery(jpql, Produto.class);

        var result = query.getResultList();

        assertFalse(result.isEmpty());
    }

    @Test
    public void usarIsEmpty() {
        var jpql = "select p from Produto p where p.categorias is empty";
        TypedQuery<Produto> query = this.entityManager.createQuery(jpql, Produto.class);

        var result = query.getResultList();

        assertTrue(result.size() == 1);
    }
}
