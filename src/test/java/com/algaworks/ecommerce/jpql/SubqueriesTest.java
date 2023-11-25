package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class SubqueriesTest extends EntityManagerTest {

    @Test
    public void pesquisarComAny() {
        //podemos usar o any ou some
        //todos os produtos que ja foram vendidos uma vez com preço atual, caso queria diferente use  <>
        final String jpql = "select p from Produto p where p.preco = ANY (select precoProduto from ItemPedido where produto = p)";
        final TypedQuery<Produto> produtos = this.entityManager.createQuery(jpql, Produto.class);
        final var result = produtos.getResultList();

        Assert.assertFalse(result.isEmpty());
        result.forEach(s -> System.out.println("Id: " + s.getId()));
    }

    @Test
    public void pesquisarComAll() {
        //retorna tb produtos que nao estao no item pedido cuidado
        final String jpql = "select p from Produto p where p.preco = ALL (select precoProduto from ItemPedido where produto = p)";
        final TypedQuery<Produto> produtos = this.entityManager.createQuery(jpql, Produto.class);
        final var result = produtos.getResultList();

        Assert.assertFalse(result.isEmpty());
        result.forEach(s -> System.out.println("Id: " + s.getId()));
    }

    @Test
    public void pesquisaComIn() {
        final String jpql = "select p from Pedido p" +
                " where p in (" +
                " select it.pedido from ItemPedido it" +
                "   join it.produto prod" +
                " where prod.preco > 100) ";

        final TypedQuery<Pedido> typedQuery = this.entityManager.createQuery(jpql, Pedido.class);
        final List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
        lista.forEach(obj -> System.out.println("ID " + obj.getId()));
    }

    @Test
    public void pesquisarSubqueries() {
        //clientes bons
        String jpql = "select c from Cliente c" +
                " where 500 < (select sum(p.total) from Pedido p where p.cliente  = c)";

        //produto mais caro da base
        //final String jpql = "select p From Produto p where p.preco = (select max(p2.preco) From Produto p2)";


        final TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
        final var lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
        lista.forEach(p -> System.out.println("id " + p.getId() + " nome " + p.getNome()));
    }
}
