package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.mysql.cj.jdbc.admin.TimezoneDump;
import org.junit.Test;

import javax.persistence.TypedQuery;

import java.sql.Time;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class FuncoesStringsTest extends EntityManagerTest {

    @Test
    public void aplicarFuncaoData() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        var sql = "select current_date, current_time, current_timestamp from Pedido p";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(sql, Object[].class);
        var list = typedQuery.getResultList();
        assertFalse(list.isEmpty());
        list.forEach(c -> System.out.println(c[0] + " | " + c[1] + " | " + c[2]));
    }

    @Test
    public void aplicarFuncao() {
        final String jpql = "select c.nome, length(c.nome) from Categoria c where substring(c.nome, 1, 1) = 'N'";
        var query = entityManager.createQuery(jpql, Object[].class);
        var lista = query.getResultList();

        assertFalse(lista.isEmpty());

        lista.forEach(System.out::println);
    }

    @Test
    public void aplicarFuncaoCollection() {
        var jpql = "select size(p.itemPedidos) from Pedido p where size(p.itemPedidos) > 1";
        var query = entityManager.createQuery(jpql, Integer.class);

        var result = query.getResultList();

        assertEquals(1, result.size());
    }
}
