package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class GroupByTest extends EntityManagerTest {

    @Test
    public void totalVendaPorDiaECategoria() {
        String jpql = "select " +
                " concat(year(p.dataCriacao), '/', month(p.dataCriacao), '/', day(p.dataCriacao)), " +
                " concat(c.nome, ': ', sum(ip.precoProduto)) " +
                " from ItemPedido ip join ip.pedido p join ip.produto pro join pro.categorias c " +
                " group by concat(year(p.dataCriacao), '/', month(p.dataCriacao), '/', day(p.dataCriacao)), c.id " +
                " order by concat(year(p.dataCriacao), '/', month(p.dataCriacao), '/', day(p.dataCriacao)), c.nome ";

        /*
        2023-09-08T22:09, Livros: 1899.00
        2023-09-13T22:09, Livros: 499.00
        * **/

        TypedQuery<Object[]> typedQuery = this.entityManager.createQuery(jpql, Object[].class);
        var result = typedQuery.getResultList();

        Assert.assertFalse(result.isEmpty());
        result.forEach(a -> System.out.println(a[0] + ", " + a[1]));
    }

    @Test
    public void totalVendasPorCliente() {
        String jpql = "Select cl.nome, sum(p.total) from Pedido p" +
                " join p.cliente cl" +
                " group by cl.id";

        TypedQuery<Object[]> typedQuery = this.entityManager.createQuery(jpql, Object[].class);
        var result = typedQuery.getResultList();

        Assert.assertFalse(result.isEmpty());
        result.forEach(a -> System.out.println(a[0] + ", " + a[1]));
    }

    @Test
    public void agruparResultado() {
//         Quantidade de produtos por categoria.
//        String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";

//         Total de vendas por mês.
//        String jpql = "select concat(year(p.dataCriacao), '/', function('monthname', p.dataCriacao)), sum(p.total) " +
//                " from Pedido p " +
//                " group by year(p.dataCriacao), month(p.dataCriacao) ";

//         Total de vendas por categoria.
        String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
                " join ip.produto pro join pro.categorias c " +
                " group by c.id";


        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }
}
