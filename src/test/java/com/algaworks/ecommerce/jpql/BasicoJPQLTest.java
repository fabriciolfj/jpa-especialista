package com.algaworks.ecommerce.jpql;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.dto.ProjecaoProduto;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class BasicoJPQLTest extends EntityManagerTest {

    @Test
    public void usarDistinct() {
        var jpql = "select distinct p from Pedido p" +
                " join p.itemPedidos i" +
                " join i.produto prod" +
                " where prod.id in (1, 2, 3, 4)";
        final TypedQuery<Pedido> query = this.entityManager.createQuery(jpql, Pedido.class);
        final var result = query.getResultList();

        assertFalse(result.isEmpty());
        assertEquals(result.size(), 2);
    }

    @Test
    public void projetarNoDTO() {
        String jpql = "select new com.algaworks.ecommerce.dto.ProjecaoProduto(id, nome) from Produto";
        TypedQuery<ProjecaoProduto> query = entityManager.createQuery(jpql, ProjecaoProduto.class);
        var result = query.getResultList();

        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void buscaPorIdentificador() {
        final TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p where p.id = 7", Pedido.class);

        final Pedido pedido = typedQuery.getSingleResult();
        assertNotNull(pedido);
    }

    @Test
    public void mostrarDiferencaQueryTypedQuery() {
        final var jpql = "select p from Pedido p where p.id = 7";

        final TypedQuery<Pedido> typed = entityManager.createQuery(jpql, Pedido.class);
        final var result = typed.getSingleResult();

        assertNotNull(result);

        final Query query = entityManager.createQuery(jpql);

        final Pedido pedido = (Pedido) query.getSingleResult();
        assertNotNull(pedido);
    }

    @Test
    public void selectionarUmAtributoParaRetorno() {
        final var jqpl = "select p.nome from Produto p";

        final TypedQuery<String> query = entityManager.createQuery(jqpl, String.class);
        var names = query.getResultList();

        assertTrue(names.size() > 1);

        final TypedQuery<Cliente> queryClient = entityManager.createQuery("select p.cliente from Pedido p where p.id = 7", Cliente.class);
        final var cliente = queryClient.getSingleResult();

        assertNotNull(cliente);
        assertTrue(Cliente.class.equals(cliente.getClass()));

    }

    @Test
    public void projetarResultado() {
        final var jpql = "select p.id, p.nome from Produto p";
        TypedQuery<Object[]> typed = entityManager.createQuery(jpql, Object[].class);

        var result = typed.getResultList();

        assertTrue(result.size() == 2);

        result.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));
    }
}
