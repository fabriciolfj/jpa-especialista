package com.algaworks.ecommerce.criteria;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import com.mysql.cj.xdevapi.Client;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class BasicoCriteriaTest extends EntityManagerTest {

    @Test
    public void projetarResultadoTuple() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Tuple> query = builder.createTupleQuery();
        final Root<Produto> root = query.from(Produto.class);

        query.select(builder.tuple(root.get("id").alias("id"), root.get("nome").alias("nome")));

        var type = this.entityManager.createQuery(query);
        var result = type.getResultList();

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());

        result.forEach(s -> System.out.println("id " + s.get("id") + " nome " + s.get("nome")));
    }

    @Test
    public void projetarResultado() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        final Root<Produto> root = query.from(Produto.class);

        query.multiselect(root.get("id"), root.get("nome"));

        var type = this.entityManager.createQuery(query);
        var result = type.getResultList();

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());

        result.forEach(s -> System.out.println("id " + s[0] + " nome " + s[1]));
    }

    @Test
    public void selecionarUmAtributoDiferente() {
        final CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Cliente> query = builder.createQuery(Cliente.class);
        final Root<Pedido> root = query.from(Pedido.class);

        query.select(root.get("cliente"));
        query.where(builder.equal(root.get("id"), 7));

        var type = this.entityManager.createQuery(query);
        var result = type.getSingleResult();

        Assert.assertNotNull(result);
    }

    @Test
    public void buscarPorIdentificador() {
        final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        final Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 7));

        final TypedQuery<Pedido> query = this.entityManager.createQuery(criteriaQuery);
        final var result = query.getResultList();

        Assert.assertFalse(result.isEmpty());
    }
}
